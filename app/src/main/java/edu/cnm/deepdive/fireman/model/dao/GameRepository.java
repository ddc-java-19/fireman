package edu.cnm.deepdive.fireman.model.dao;

import edu.cnm.deepdive.fireman.model.entity.Game;
import java.util.Optional;
import java.util.UUID;

public interface GameRepository extends JpaRepository<Game, Long>{

  Optional<Game> findByExternalKEy(UUID externalKey);

}
