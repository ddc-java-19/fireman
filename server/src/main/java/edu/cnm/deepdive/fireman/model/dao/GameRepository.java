package edu.cnm.deepdive.fireman.model.dao;

import edu.cnm.deepdive.fireman.model.entity.Game;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {

  Optional<Game> findByExternalKey(UUID externalKey);

}
