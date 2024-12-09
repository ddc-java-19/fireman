package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.Move;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.UUID;

public interface AbstractGameService {

  Game startJoin(Game game, User user);

  Game get(UUID key, User user);

  Game move(UUID key, Move move, User user);

  int getMoveCount(UUID key, User user);

  Game surrender(UUID key, User user);
}
