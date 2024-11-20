package edu.cnm.deepdive.fireman.model.dao;

import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GameRepository extends JpaRepository<Game, Long> {

  String CURRENT_GAMES = """
      SELECT
      g
      FROM
        Game AS g
      WHERE
        (g.arsonist = :user
      OR
        g.fireman = :user)
      AND
        g.finished is null
      """;

  String OPEN_GAMES = """
      SELECT
      g
      FROM
        Game AS g
      WHERE
        (g.arsonist is not null
      AND
       g.fireman is null)
      OR
        (g.fireman is not null
      AND
        g.arsonist is null)
      """;

  String FIND_BY_USER = """
      SELECT
        fireman = :fireman
      AND
        arsonist = :arsonist
      WHERE
        g.fireman = :fireman
      OR
      g.arsonist = :arsonist
      """;

  @Query(CURRENT_GAMES)
  List<Game> findCurrentGames(User user);

  @Query(OPEN_GAMES)
  List<Game> findOpenGames();

  Optional<Game> findByExternalKeyAndArsonistOrFireman(UUID key, User arsonist, User fireman);
}
