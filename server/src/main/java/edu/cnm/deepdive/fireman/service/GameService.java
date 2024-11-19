package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.dao.GameRepository;
import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameService {

 private final GameRepository gameRepository;

  public GameService(GameRepository gameRepository) {
    this.gameRepository = gameRepository;

  }


  @Autowired
 public Game startJoin(Game game) {
  Game gameToPlay;
  List<Game> games = GameRepository.findCurrentGames(User);
  if (!currentGames.isEmpty()) {
   gameToPlay = currentGame.getFirst();
  } else {
   List<Game> openGames = GameRepository.findOpenGames();
   gameToPlay = openGames.isEmpty() ? game : openGames.getFirst();
  }
  return gameToPlay;
 }

 @Override
 public Game get(UUID externalKey) {
  return gameRepository
      .findByExternalKeyAndUser(externalKey, userService.getCurrent())
      .orElseThrow();

}
