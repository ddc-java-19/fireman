package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.PlotState;
import edu.cnm.deepdive.fireman.model.Wind;
import edu.cnm.deepdive.fireman.model.dao.GameRepository;
import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.Move;
import edu.cnm.deepdive.fireman.model.entity.Plot;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.random.RandomGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("service")
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final AbstractUserService userService;
  private final RandomGenerator rng;
  private final int boardSize;
  private final float initialFireProbability;

  @Autowired
  public GameService(GameRepository gameRepository, AbstractUserService userService,
      RandomGenerator rng, @Value("${fireman.board-size}") int boardSize,
      @Value("${fireman.initial-fire-probablility}") float initialFireProbability) {
    this.gameRepository = gameRepository;
    this.userService = userService;
    this.boardSize = boardSize;
    this.rng = rng;
    this.initialFireProbability = initialFireProbability;
  }

  @Override
  public Game startJoin(Game game) {
    Game gameToPlay;
    User currentUser = userService.getCurrent();
    List<Game> games = gameRepository.findCurrentGames(currentUser);
    if (!games.isEmpty()) {
      gameToPlay = games.getFirst();
    } else {
      List<Game> openGames = gameRepository.findOpenGames();
      if (openGames.isEmpty()) {
        gameToPlay = game;
        gameToPlay.setArsonist(currentUser);
        gameToPlay.setWind(Wind.NORTH);
        // TODO: 11/20/2024 randomize wind direction on game start.
      } else {
        gameToPlay = openGames.getFirst();
        gameToPlay.setFireman(currentUser);
        List<Plot> plots = gameToPlay.getPlots();
        for (int rowIndex = 0; rowIndex < boardSize; rowIndex++) {
          for (int colIndex = 0; colIndex < boardSize; colIndex++) {
            Plot plot = new Plot();
            plot.setRow(rowIndex);
            plot.setColumn(colIndex);
            plot.setGame(gameToPlay);
            plot.setPlotState(
                (rng.nextFloat() < initialFireProbability) ? PlotState.ON_FIRE: PlotState.BURNABLE);
            plots.add(plot);
          }
        }
      }
    }
    return gameRepository.save(gameToPlay);
  }

  public Game get(UUID key) {
    return gameRepository.findGameByKeyAndUser(key, userService.getCurrent())
        .orElseThrow();
  }

  public Game move(UUID key, Move move) {
    User currentUser = userService.getCurrent();
    return gameRepository.findStartedGameByKeyAndUser(key, currentUser)
        .map((game) -> {
          Integer column = move.getColumn();
          Integer row = move.getRow();
          boolean userIsFireman = game.getFireman().equals(currentUser);
          validateMove(game, userIsFireman, row, column);
          if (userIsFireman) {
            handleFiremanMove(game, row, column);
            handleTime(game, row, column);
          } else {
            handleArsonistMove(game, row, column);
          }
          // TODO: 11/21/2024 set fields of Move object. Add to MovesList in the game.
          game.setFiremansTurn(!game.isFiremansTurn());
          return gameRepository.save(game);
        })
        .orElseThrow();
  }

  private static void handleArsonistMove(Game game, Integer row, Integer column) {
    game.getPlots()
        .stream()
        .filter((plot) -> plot.getRow() == row && plot.getColumn() == column)
        .forEach(plot -> plot.setPlotState(plot.getPlotState().nextState(false)));
  }

  private static void handleTime(Game game, Integer row, Integer column) {
    Wind wind = game.getWind();
    boolean spreadInRow = wind.getColumnOffset() != 0;
    int rowLowerBound;
    int colLowerBound;
    int rowUpperBound;
    int colUpperBound;
    if (spreadInRow) {
      rowLowerBound = row - 1;
      rowUpperBound = row + 1;
      colLowerBound = column + wind.getColumnOffset();
      colUpperBound = colLowerBound;
    } else {
      rowLowerBound = row + wind.getRowOffset();
      rowUpperBound = rowLowerBound;
      colLowerBound = column - 1;
      colUpperBound = column + 1;
    }
    game.getPlots()
        .stream()
        .filter((plot) -> plot.getRow() >= rowLowerBound && plot.getRow() <= rowUpperBound
            && plot.getColumn() >= colLowerBound && plot.getColumn() <= colUpperBound)
        .forEach(plot -> {
          if (plot.getRow() >= rowLowerBound && plot.getRow() <= rowUpperBound
              && plot.getColumn() >= colLowerBound && plot.getColumn() <= colUpperBound) {
            plot.setPlotState(plot.getPlotState().nextStateSpread());
          } else {
            plot.setPlotState(plot.getPlotState().nextState(null));
          }
        });
  }

  private static void handleFiremanMove(Game game, Integer row, Integer column) {
    if (row == null) {
      game.getPlots()
          .stream()
          .filter((plot) -> plot.getColumn() == column)
          .forEach(plot -> plot.setPlotState(plot.getPlotState().nextState(true)));
    } else if (column == null) {
      game.getPlots()
          .stream()
          .filter((plot) -> plot.getRow() == row)
          .forEach(plot -> plot.setPlotState(plot.getPlotState().nextState(true)));
    } else {
      // TODO: 11/21/2024 waterbomb spread to be determined
      game.getPlots()
          .stream()
          .filter((plot) -> plot.getRow() == row
              && plot.getColumn() == column)
          .forEach(plot -> plot.setPlotState(plot.getPlotState().nextState(true)));
    }
  }

  private void validateMove(Game game, boolean userIsFireman, Integer row, Integer column) {
    if (userIsFireman != game.isFiremansTurn()) {
      throw new OutOfTurnException();
    }
    if (game.isCompleted()) {
      throw new GameOverException();
    }
    if (row != null && (row < 0 || row >= boardSize)) {
      throw new OutOfBoundsException();
    }
    if (column != null && (column < 0 || column >= boardSize)) {
      throw new OutOfBoundsException();
    }
    if (row == null && column == null) {
      throw new InsufficientInformationException();
    }
    if (!userIsFireman && (row == null || column == null)) {
      throw new InsufficientInformationException();
    }
  }


}
