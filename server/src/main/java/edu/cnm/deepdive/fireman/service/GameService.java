package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.Wind;
import edu.cnm.deepdive.fireman.model.dao.GameRepository;
import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;
  private final UserService userService;
  private User fireman;

  @Autowired
  public GameService(GameRepository gameRepository, UserService userService) {
    this.gameRepository = gameRepository;
    this.userService = userService;
  }

  public Game startJoin(Game game, User user) {
    Game gameToPlay;
    List<Game> games = gameRepository.findCurrentGames(user);
    if (!games.isEmpty()) {
      gameToPlay = games.getFirst();
    } else {
      List<Game> openGames = gameRepository.findOpenGames();
      if(openGames.isEmpty()) {
        gameToPlay = game;
        gameToPlay.setArsonist(user);
        gameToPlay.setWind(Wind.NORTH);
        // TODO: 11/20/2024 randomize wind direction on game start.
      } else {
        gameToPlay = openGames.getFirst();
        gameToPlay.setFireman(user);
      }
    }
    return gameRepository.save(gameToPlay);
  }

  public Game get(User fireman, UUID key) {
    this.fireman = fireman;
    return gameRepository.findGameByExternalKeyAndFireman(UUID key, userService.getCurrent())
        .orElseThrow();

  }

  public Game get(UUID key, User arsonist, UserService userService){
    return gameRepository.findGameByExternalKeyAndArsonist(UUID arsonist.getExternalKey(key), userService.getCurrent())
        .orElseThrow();
  }

  public User getFireman() {
    return fireman;
  }

  public void setFireman(User fireman) {
    this.fireman = fireman;
  }
}
