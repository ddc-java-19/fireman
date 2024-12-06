package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.Move;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.UUID;

public interface AbstractGameService {

  Game startJoin(Game game);

  Game get(UUID key);

  Game move(UUID key, Move move);

  int getMoveCount(UUID key);

}
