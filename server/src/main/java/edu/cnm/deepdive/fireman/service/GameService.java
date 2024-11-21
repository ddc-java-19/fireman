package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.PlotState;
import edu.cnm.deepdive.fireman.model.Wind;
import edu.cnm.deepdive.fireman.model.dao.GameRepository;
import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.Move;
import edu.cnm.deepdive.fireman.model.entity.Plot;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final AbstractUserService userService;
  private final int boardSize;

  @Autowired
  public GameService(GameRepository gameRepository, AbstractUserService userService,
      @Value("${fireman.board-size}") int boardSize) {
    this.gameRepository = gameRepository;
    this.userService = userService;
    this.boardSize = boardSize;
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
            plot.setPlotState(PlotState.BURNABLE);
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
              game.getPlots()
                  .stream()
                  .filter((plot) -> plot.getRow() == row
                      && plot.getColumn()
                      == column) // TODO: 11/21/2024 waterbomb spread to be determined
                  .forEach(plot -> plot.setPlotState(plot.getPlotState().nextState(true)));
            }
            // TODO: 11/21/2024 Decide how to handle adjacency
            game.getPlots()
                .forEach(plot -> plot.setPlotState(plot.getPlotState().nextState(null)));
          } else {
            game.getPlots()
                .stream()
                .filter((plot) -> plot.getRow() == row && plot.getColumn() == column)
                .forEach(plot -> plot.setPlotState(plot.getPlotState().nextState(false)));
          }
          return gameRepository.save(game);
        })
        .orElseThrow();
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
