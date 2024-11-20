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
        (g.arsonist = arsonist
      OR
        g.fireman = fireman)
      AND
        g.finished != null
      """;

  String OPEN_GAMES = """
      SELECT
      g
      FROM
        Game AS g
      WHERE
        (g.arsonist != null
      AND
       g.fireman = null)
      OR
        (g.fireman != null
      AND
        g.arsonist = null)
      """;


  @Query(CURRENT_GAMES)
  List<Game> findCurrentGames(User arsonist, User fireman);

  @Query(OPEN_GAMES)
  List<Game> findOpenGames();


}
