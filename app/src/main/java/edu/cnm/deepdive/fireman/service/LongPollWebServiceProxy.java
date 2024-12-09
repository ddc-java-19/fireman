package edu.cnm.deepdive.fireman.service;

import edu.cnm.deepdive.fireman.model.domain.Game;
import edu.cnm.deepdive.fireman.model.domain.Move;
import edu.cnm.deepdive.fireman.model.domain.User;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LongPollWebServiceProxy {

  @GET("games/{key}")
  Single<Game> getGame(@Path("key") String key, @Query("moveCount") int moveCount, @Header("Authorization") String bearerToken);

}
