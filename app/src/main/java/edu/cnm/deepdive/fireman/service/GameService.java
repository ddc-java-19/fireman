package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.domain.Game;
import edu.cnm.deepdive.fireman.model.domain.Move;
import edu.cnm.deepdive.fireman.model.domain.User;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameService {

  private final GoogleSignInService signInService;
  private final WebServiceProxy webServiceProxy;
  private final LongPollWebServiceProxy longPollWebServiceProxy;

  private Game game;

  @Inject
  GameService(GoogleSignInService signInService, WebServiceProxy webServiceProxy,
      LongPollWebServiceProxy longPollWebServiceProxy) {
    this.signInService = signInService;
    this.webServiceProxy = webServiceProxy;
    this.longPollWebServiceProxy = longPollWebServiceProxy;
  }

  public Single<Game> startGame() {
    return signInService
        .refreshToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webServiceProxy.getProfile(token)
            .flatMap((user) -> webServiceProxy.startGame(new Game(), token)
                .map((game) -> {
                  game.setUser(user);
                  this.game = game;
                  return game;
                })
            ));
  }

  public Single<Game> loadGame(String key) {
    return signInService
        .refreshToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webServiceProxy.getGame(key, token));
  }

  public Single<Game> move(Move move) {
    return signInService
        .refreshToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webServiceProxy.move(game.getKey(), move, token));
  }

  public Single<Game> loadGame(String key, int moveCount){
    return signInService
        .refreshToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> longPollWebServiceProxy.getGame(key, moveCount, token))
        .doOnSuccess((game) -> {
          game.setUser(this.game.getUser());
          this.game = game;
        });
  }


}
