package edu.cnm.deepdive.fireman.controller;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.snackbar.Snackbar;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.fireman.R;
import edu.cnm.deepdive.fireman.databinding.ActivityLoginBinding;
import edu.cnm.deepdive.fireman.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;
  private LoginViewModel viewModel;
  private ActivityResultLauncher<Intent> launcher;
  private boolean silent;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    getLifecycle().addObserver(viewModel);
    launcher = registerForActivityResult(new StartActivityForResult(), viewModel::completeSignIn);
    silent = true;
    viewModel
        .getAccount()
        .observe(this, this::handleAccount);
    viewModel
        .getThrowable()
        .observe(this, this::handleFailure);
  }

  private void handleAccount(GoogleSignInAccount account) {
    if (account != null) {
      openMainActivity();
    }
  }

  private void handleFailure(Throwable throwable) {
    if (silent) {
      silent = false;
      binding = ActivityLoginBinding.inflate(getLayoutInflater());
      binding.signIn.setOnClickListener((v) -> viewModel.startSignIn(launcher));
      setContentView(binding.getRoot());
    } else {
      informAuthenticationFailure();
    }
  }

  private void openMainActivity() {
    Intent intent = new Intent(this, MainActivity.class)
        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
    startActivity(intent);
  }

  private void informAuthenticationFailure() {
    Snackbar.make(binding.getRoot(), R.string.authentication_failure_message, Snackbar.LENGTH_LONG)
        .show();
  }

}