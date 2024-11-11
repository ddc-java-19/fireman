package edu.cnm.deepdive.fireman.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import dagger.hilt.android.qualifiers.ApplicationContext;
import edu.cnm.deepdive.fireman.R;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;
import io.reactivex.rxjava3.schedulers.Schedulers;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @noinspection deprecation
 */
@Singleton
public class GoogleSignInService {

  private static final String BEARER_TOKEN_FORMAT = "Bearer %s";
  private static final String TAG = GoogleSignInService.class.getSimpleName();
  /**
   * @noinspection deprecation
   */
  private final GoogleSignInClient client;

  @Inject
  GoogleSignInService(@ApplicationContext Context context) {
    String clientId = context.getString(R.string.client_id);
    GoogleSignInOptions options = new GoogleSignInOptions.Builder()
        .requestEmail()
        .requestId()
        .requestProfile()
        .requestIdToken(clientId)
        .build();
    client = GoogleSignIn.getClient(context, options);
  }

  public Single<GoogleSignInAccount> refresh() {
    return Single.create((SingleEmitter<GoogleSignInAccount> emitter) -> {
          client
              .silentSignIn()
              .addOnSuccessListener(t -> {
                emitter.onSuccess(t);
                Log.d(TAG, t.getIdToken()); // FIXME Remove after testing.
              })
              .addOnFailureListener(emitter::onError);
        })
        .observeOn(Schedulers.io());
  }

  public Single<String> refreshToken() {
    return refresh()
        .map((account) -> String.format(BEARER_TOKEN_FORMAT, account.getIdToken()));
  }

  public void startSignIn(ActivityResultLauncher<Intent> launcher) {
    launcher.launch(client.getSignInIntent());
  }

  public Single<GoogleSignInAccount> completeSignIn(ActivityResult result) {
    return Single.create((SingleEmitter<GoogleSignInAccount> emitter) -> {
          try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(
                result.getData());
            GoogleSignInAccount account = task.getResult(ApiException.class);
            emitter.onSuccess(account);
            Log.d(TAG, account.getIdToken()); // FIXME Remove after testing.
          } catch (ApiException e) {
            emitter.onError(e);
          }
        })
        .observeOn(Schedulers.io());
  }

  public Completable signOut() {
    return Completable.create((emitter) ->
            client
                .signOut()
                .addOnSuccessListener((ignored) -> emitter.onComplete())
                .addOnFailureListener(emitter::onError)
        )
        .observeOn(Schedulers.io());
  }
}
