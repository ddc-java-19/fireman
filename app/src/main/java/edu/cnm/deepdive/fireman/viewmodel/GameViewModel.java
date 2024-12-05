package edu.cnm.deepdive.fireman.viewmodel;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.fireman.model.domain.Game;
import edu.cnm.deepdive.fireman.model.domain.Move;
import edu.cnm.deepdive.fireman.service.GameService;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javax.inject.Inject;

@HiltViewModel
public class GameViewModel extends ViewModel implements DefaultLifecycleObserver {

  private static final String TAG = GameViewModel.class.getSimpleName();

  private final GameService gameService;
  private final MutableLiveData<Game> game;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  @Inject
  GameViewModel(GameService gameService) {
    this.gameService = gameService;
    game = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    startGame();
  }

  public void startGame(){
    throwable.setValue(null);
    gameService.startGame()
        .subscribe(
            game::postValue,
            this::postThrowable,
            pending
        );
  }

  public void submitMove(Integer row, Integer column) {
    Move move = new Move(row, column);
    throwable.setValue(null);
    gameService.move(move)
        .subscribe(
            game::postValue,
            this::postThrowable,
            pending
        );
  }

  // TODO: 12/4/2024 implement loadGame() and move() from GameService.
//  public void loadGame(String key){
//    throwable.setValue(null);
//    gameService
//        .loadGame(key)
//        .subscribe(
//
//
//            )
//
//  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    pending.clear();
    DefaultLifecycleObserver.super.onStop(owner);
  }

  public LiveData<Game> getGame() {
    return game;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  private void postThrowable(Throwable throwable) {
    Log.e(TAG, throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }


}
