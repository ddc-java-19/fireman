package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.domain.Game;
import edu.cnm.deepdive.fireman.model.domain.Move;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class GameService {

  private final GoogleSignInService signInService;
  private final WebServiceProxy webServiceProxy;

  @Inject
  GameService(GoogleSignInService signInService, WebServiceProxy webServiceProxy) {
    this.signInService = signInService;
    this.webServiceProxy = webServiceProxy;
  }

  public Single<Game> startGame() {
    return signInService
        .refreshToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webServiceProxy.startGame(new Game(), token));
  }

  public Single<Game> loadGame(String key) {
    return signInService
        .refreshToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webServiceProxy.getGame(key, token));
  }

  public Single<Game> move(String key, Move move){
    return signInService
        .refreshToken()
        .observeOn(Schedulers.io())
        .flatMap((token) -> webServiceProxy.move(key, move, token));
  }


}
