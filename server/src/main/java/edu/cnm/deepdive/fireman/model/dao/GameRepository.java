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
        (g.arsonist is null  AND g.fireman is not null)
        OR (g.arsonist is not null AND g.fireman is null)
      
      """;

  String FIND_BY_USER_ARSONIST = """
      SELECT
        arsonist = :arsonist
      WHERE
        externalKey = :arsonist
      AND
        g.arsonist = :arsonist
      """;

  String FIND_BY_USER_FIREMAN = """
      SELECT
        fireman = :fireman
      WHERE
        externalKey = :fireman
      AND
        g.fireman = :fireman
      """;

  String FIND_BY_USER_AND_KEY = """
      SELECT
      g
      FROM
      Game AS g
      WHERE
      (g.arsonist = :user OR g.fireman = :user)
      AND
      (g.externalKey = :key)
    
      """;

  @Query(CURRENT_GAMES)
  List<Game> findCurrentGames(User user);

  @Query(OPEN_GAMES)
  List<Game> findOpenGames();

//  @Query(FIND_BY_USER_ARSONIST)
//  Optional<Game> findGameByExternalKeyAndArsonist(UUID key, User arsonist);
//
//  @Query(FIND_BY_USER_FIREMAN)
//  Optional<Game> findGameByExternalKeyAndFireman(UUID key, User fireman);

  @Query(FIND_BY_USER_AND_KEY)
  Optional<Game> findGameByKeyAndUser(UUID key, User user);
}
