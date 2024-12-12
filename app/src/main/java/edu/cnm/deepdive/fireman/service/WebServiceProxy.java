package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.domain.Game;
import edu.cnm.deepdive.fireman.model.domain.Move;
import edu.cnm.deepdive.fireman.model.domain.User;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface WebServiceProxy {

  @POST("games")
  Single<Game> startGame(@Body Game game, @Header("Authorization") String bearerToken);

  @GET("games/{key}")
  Single<Game> getGame(@Path("key") String key, @Header("Authorization") String bearerToken);

  @POST("games/{key}/surrender")
  Completable surrender(@Path("key") String key, @Header("Authorization") String bearerToken);

  @POST("games/{key}/moves")
  Single<Game> move(@Path("key") String key, @Body Move move, @Header("Authorization") String bearerToken);

  @GET("users/me")
  Single<User> getProfile(@Header("Authorization") String bearerToken);
}
