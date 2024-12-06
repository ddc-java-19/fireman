package edu.cnm.deepdive.fireman.controller;

import edu.cnm.deepdive.fireman.model.entity.Game;
import edu.cnm.deepdive.fireman.model.entity.Move;
import edu.cnm.deepdive.fireman.service.AbstractGameService;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
@RequestMapping("/games")
@Profile("service")
public class GameController {

  private static final long TIMEOUT = 15000L;
  private static final int POOL_SIZE= 4;
  private static final long INTERVAL = 3000L;

  private final AbstractGameService gameService;
  private final ScheduledExecutorService executor;

  public GameController(AbstractGameService gameService) {
    this.gameService = gameService;
    executor = Executors.newScheduledThreadPool(POOL_SIZE);
  }

  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Game post(@RequestBody Game game) {
    Game created = gameService.startJoin(game);
//    URI location = WebMvcLinkBuilder.linkTo(
//        WebMvcLinkBuilder.methodOn(getClass()).get(created.getExternalKey())
//    ).toUri();
    return created;
  }

  @GetMapping(path = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Game get(@PathVariable UUID key) {
    return gameService.get(key);
  }

  @GetMapping(path = "/{key}", produces = MediaType.APPLICATION_JSON_VALUE, params = {"moveCount"})
  public DeferredResult<Game> get(@PathVariable UUID key, @RequestParam int moveCount) {
    DeferredResult<Game> deferred = new DeferredResult<>(TIMEOUT);
    deferred.onTimeout(() -> deferred.setResult(gameService.get(key)));
    ScheduledFuture<?>[] future = new ScheduledFuture<?>[1];
    Runnable checkMoveCount = () -> {
      if(gameService.getMoveCount(key) != moveCount) {
        deferred.setResult(gameService.get(key));
        future[0].cancel(true);
      }
    };
    future[0] = executor.scheduleAtFixedRate(checkMoveCount, INTERVAL, INTERVAL, TimeUnit.MILLISECONDS);
    executor.schedule(() -> future[0].cancel(true), TIMEOUT, TimeUnit.MILLISECONDS);
    return deferred;
  }

  @PostMapping(path = "/{key}/moves", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Game post(@PathVariable UUID key, @RequestBody Move move) {
    return gameService.move(key, move);
  }


}
