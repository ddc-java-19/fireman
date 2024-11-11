package edu.cnm.deepdive.fireman.viewmodel;

import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import dagger.hilt.android.lifecycle.HiltViewModel;
import edu.cnm.deepdive.fireman.service.GoogleSignInService;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import javax.inject.Inject;

@HiltViewModel
public class LoginViewModel extends ViewModel implements DefaultLifecycleObserver {

  private static final String TAG = LoginViewModel.class.getSimpleName();

  private final GoogleSignInService signInService;
  private final MutableLiveData<GoogleSignInAccount> account;
  private final MutableLiveData<Throwable> throwable;
  private final CompositeDisposable pending;

  @Inject
  LoginViewModel(GoogleSignInService signInService) {
    this.signInService = signInService;
    account = new MutableLiveData<>();
    throwable = new MutableLiveData<>();
    pending = new CompositeDisposable();
    refresh();
  }

  public LiveData<GoogleSignInAccount> getAccount() {
    return account;
  }

  public LiveData<Throwable> getThrowable() {
    return throwable;
  }

  public void refresh() {
    throwable.setValue(null);
    signInService
        .refresh()
        .subscribe(
            account::postValue,
            this::postThrowable,
            pending
        );
  }

  public void startSignIn(ActivityResultLauncher<Intent> launcher) {
    throwable.setValue(null);
    signInService.startSignIn(launcher);
  }

  public void completeSignIn(ActivityResult result) {
    throwable.setValue(null);
    signInService
        .completeSignIn(result)
        .subscribe(
            account::postValue,
            this::postThrowable,
            pending
        );
  }

  public void signOut() {
    throwable.setValue(null);
    signInService
        .signOut()
        .doFinally(() -> account.postValue(null))
        .subscribe(
            () -> {},
            this::postThrowable,
            pending
        );
  }

  @Override
  public void onStop(@NonNull LifecycleOwner owner) {
    pending.clear();
    DefaultLifecycleObserver.super.onStop(owner);
  }

  private void postThrowable(Throwable throwable) {
    Log.e(TAG, throwable.getMessage(), throwable);
    this.throwable.postValue(throwable);
  }


}
