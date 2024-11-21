package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.Wind;
import edu.cnm.deepdive.fireman.model.dao.GameRepository;
import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.Move;
import edu.cnm.deepdive.fireman.model.entity.Plot;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.List;
import java.util.UUID;
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
            plot.setBurnable(true);
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

  public Game move(Move move) {
    throw new UnsupportedOperationException("not yet implemented");
  }

}
