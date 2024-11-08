package edu.cnm.deepdive.fireman.controller;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.snackbar.Snackbar;
import edu.cnm.deepdive.fireman.R;
import edu.cnm.deepdive.fireman.databinding.ActivityLoginBinding;


public class LoginActivity extends AppCompatActivity {

  private ActivityLoginBinding binding;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityLoginBinding.inflate(getLayoutInflater());
    // TODO: 11/7/2024 Attach listeners to ui widgets.
    setContentView(binding.getRoot());
  }

  // TODO: 11/7/2024 Define a method to start login process.

  // TODO: 11/7/2024 Define a method to handle result of login process.

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