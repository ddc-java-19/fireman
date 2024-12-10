package edu.cnm.deepdive.fireman.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.AppBarConfiguration.Builder;
import androidx.navigation.ui.NavigationUI;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.fireman.R;
import edu.cnm.deepdive.fireman.viewmodel.LoginViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private LoginViewModel viewModel;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setupNavigation();
    setUpViewModel();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.main_options, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    boolean handled = true;
    if (item.getItemId() == R.id.sign_out) {
    viewModel.signOut();
    } else {
      handled = super.onOptionsItemSelected(item);
    }
    return handled;
  }

  private void setupNavigation() {
    NavHostFragment fragment = (NavHostFragment)
        getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
    //noinspection DataFlowIssue
    NavController navController = fragment.getNavController();
    AppBarConfiguration appBarConfig = new Builder(R.id.game_fragment,
        R.id.stats_fragment)
        .build();
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfig);
  }

  private void setUpViewModel() {
    viewModel = new ViewModelProvider(this).get(LoginViewModel.class);
    viewModel.getAccount()
        .observe(this, (account) -> {
          if (account == null) {
            Intent intent = new Intent(this, LoginActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
          }
        });
  }
}