package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.PlotState;
import edu.cnm.deepdive.fireman.model.Wind;
import edu.cnm.deepdive.fireman.model.dao.GameRepository;
import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.Move;
import edu.cnm.deepdive.fireman.model.entity.Plot;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("service")
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final RandomGenerator rng;
  private final int boardSize;
  private final float initialFireProbability;

  @Autowired
  public GameService(GameRepository gameRepository, AbstractUserService userService,
      RandomGenerator rng, @Value("${fireman.board-size}") int boardSize,
      @Value("${fireman.initial-fire-probablility}") float initialFireProbability) {
    this.gameRepository = gameRepository;
    this.boardSize = boardSize;
    this.rng = rng;
    this.initialFireProbability = initialFireProbability;
  }

  @Override
  public Game startJoin(Game game, User currentUser) {
    Game gameToPlay;
    List<Game> games = gameRepository.findCurrentGames(currentUser);
    if (!games.isEmpty()) {
      gameToPlay = games.getFirst();
    } else {
      List<Game> openGames = gameRepository.findOpenGames();
      if (openGames.isEmpty()) {
        gameToPlay = game;
        gameToPlay.setArsonist(currentUser);
        Wind[] winds = Wind.values();
        gameToPlay.setWind(winds[rng.nextInt(winds.length)]);
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
            plot.setPlotState(PlotState.BURNABLE);
            plots.add(plot);
          }
        }
        Collections.shuffle(plots, rng);
        int initialOnFirePlots = Math.round(initialFireProbability * plots.size());
        plots.subList(0, initialOnFirePlots)
            .forEach((plot) -> plot.setPlotState(PlotState.ON_FIRE));
        game.setMoveCount(game.getMoveCount() + 1);
      }
    }
    return gameRepository.save(gameToPlay);
  }

  @Override
  public Game get(UUID key, User user) {
    return gameRepository.findGameByKeyAndUser(key, user)
        .orElseThrow();
  }

  @Override
  public Game move(UUID key, Move move, User currentUser) {
    return gameRepository.findStartedGameByKeyAndUser(key, currentUser)
        .map((game) -> {
          Integer column = move.getColumn();
          Integer row = move.getRow();
          boolean userIsFireman = game.getFireman().equals(currentUser);
          validateMove(game, userIsFireman, row, column);
          if (userIsFireman) {
            handleFiremanMove(game, row, column);
            handleTime(game);
          } else {
            handleArsonistMove(game, row, column);
          }
          // TODO: 11/21/2024 set fields of Move object. Add to MovesList in the game.
          game.setFiremansTurn(!game.isFiremansTurn());
          game.setMoveCount(game.getMoveCount() + 1);
          int onFire = countPlotState(game.getPlots(), PlotState.ON_FIRE);
          if (onFire == 0) {
            int charred = countPlotState(game.getPlots(), PlotState.CHARRED);
            game.setFiremanWin(charred <= game.getPlots().size() / 2);
            game.setFinished(Instant.now());
          }
          return gameRepository.save(game);
        })
        .orElseThrow();
  }

  @Override
  public int getMoveCount(UUID key, User currentUser) {
    return gameRepository.getMoveCount(key, currentUser)
        .orElseThrow();
  }

  @Override
  public Game surrender(UUID key, User currentUser) {
    return gameRepository.findGameByKeyAndUser(key, currentUser)
        .map((game) -> {
          if (game.isCompleted()) {
            throw new GameOverException("Game is completed");
          }
          boolean firemanSurrender = game.getFireman().equals(currentUser);
          game.setFiremanSurrender(firemanSurrender);
          game.setFiremanWin(!firemanSurrender);
          game.setFinished(Instant.now());
          game.setMoveCount(game.getMoveCount() + 1);
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

  private static void handleTime(Game game) {
    Wind wind = game.getWind();
    boolean spreadInRow = wind.getColumnOffset() != 0;
    int rowLowerBound;
    int colLowerBound;
    int rowUpperBound;
    int colUpperBound;
    if (spreadInRow) {
      rowLowerBound = -1;
      rowUpperBound = 1;
      colLowerBound = -wind.getColumnOffset();
      colUpperBound = colLowerBound;
    } else {
      rowLowerBound = -wind.getRowOffset();
      rowUpperBound = rowLowerBound;
      colLowerBound = -1;
      colUpperBound = 1;
    }
    Set<Plot> spreadPlots = game.getPlots()
        .stream()
        .filter((plot) -> plot.getPlotState() == PlotState.BURNABLE
            && game.getPlots()
            .stream()
            .anyMatch((p) -> p.getPlotState() == PlotState.ON_FIRE
                && p.getRow() >= plot.getRow() + rowLowerBound
                && p.getRow() <= plot.getRow() + rowUpperBound
                && p.getColumn() >= plot.getColumn() + colLowerBound
                && p.getColumn() <= plot.getColumn() + colUpperBound
            ))
        .collect(Collectors.toSet());
    Set<Plot> nonSpreadPlots = game.getPlots()
        .stream()
        .filter((plot) -> !spreadPlots.contains(plot))
        .collect(Collectors.toSet());
    spreadPlots.forEach((plot) -> plot.setPlotState(plot.getPlotState().nextStateSpread()));
    nonSpreadPlots.forEach((plot) -> plot.setPlotState(plot.getPlotState().nextState(null)));
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

  private int countPlotState(List<Plot> plots, PlotState state) {
    return (int) plots
        .stream()
        .filter((plot) -> plot.getPlotState() == state)
        .count();
  }


}
