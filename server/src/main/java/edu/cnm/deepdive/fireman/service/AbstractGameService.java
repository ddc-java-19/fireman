package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.UUID;

public interface AbstractGameService {

  Game startJoin(Game game, User user);

  public Game get(UUID key);

  Game getFireman(UUID key, User fireman);

  Game getArsonist(UUID key, User arsonist);
}
