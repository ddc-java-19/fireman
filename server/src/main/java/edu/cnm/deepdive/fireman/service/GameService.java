package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.dao.GameRepository;
import edu.cnm.deepdive.fireman.model.dao.UserRepository;
import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService implements AbstractGameService {

  private final GameRepository gameRepository;

  @Autowired
  public GameService(GameRepository gameRepository) {
    this.gameRepository = gameRepository;
  }

  public Game startJoin(Game game, User user) {
    Game gameToPlay;
    List<Game> games = gameRepository.findCurrentGames(user, user);
    if (!games.isEmpty()) {
      gameToPlay = games.getFirst();
    } else {
      List<Game> openGames = gameRepository.findOpenGames();
      if(openGames.isEmpty()) {
        gameToPlay = game;
        gameToPlay.setArsonist(user);
      } else {
        gameToPlay = openGames.getFirst();
        gameToPlay.setFireman(user);
      }
    }
    return gameToPlay;
  }



  }
