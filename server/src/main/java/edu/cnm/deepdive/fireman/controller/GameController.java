package edu.cnm.deepdive.fireman.controller;

import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.service.AbstractGameService;
import edu.cnm.deepdive.fireman.service.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameController {

  private final AbstractGameService gameService;
  private final UserService userService;

  public GameController(AbstractGameService gameService,
      UserService userService) {
    this.gameService = gameService;
    this.userService = userService;
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Game post(@RequestBody Game game) {
    Game created = gameService.startJoin(game, userService.getCurrent());
//    URI location = WebMvcLinkBuilder.linkTo(
//        WebMvcLinkBuilder.methodOn(getClass()).get(created.getExternalKey())
//    ).toUri();
    return created;
  }


}
