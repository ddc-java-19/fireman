package edu.cnm.deepdive.fireman.controller;

import edu.cnm.deepdive.fireman.model.dao.GameRepository;
import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.User;
import edu.cnm.deepdive.fireman.service.AbstractGameService;
import edu.cnm.deepdive.fireman.service.UserService;
import java.net.URI;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

  private final AbstractGameService gameService;
  private final UserController userController;
  private final UserService userService;
  private final GameRepository gameRepository;

  @Autowired
  public GameController(AbstractGameService gameService, UserController userController,
      UserService userService, GameRepository gameRepository) {
    this.gameService = gameService;
    this.userController = userController;
    this.userService = userService;
    this.gameRepository = gameRepository;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Game> post(@RequestBody Game game) {
    Game created = gameService.startJoin(game, userService.getCurrent());
    URI location = WebMvcLinkBuilder.linkTo(
        WebMvcLinkBuilder.methodOn(getClass()).get(created.getExternalKey())
    ).toUri();
    return ResponseEntity.created(location).body(created);
  }

  @Override
  public Game get(UUID externalKey) {
    return gameRepository
        .findByExternalKeyAndUser(externalKey, userService.getCurrent())
        .orElseThrow();


}
