package edu.cnm.deepdive.fireman.controller;

import android.os.Bundle;

import android.util.Log;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.fireman.R;
import edu.cnm.deepdive.fireman.databinding.ActivityMainBinding;
import edu.cnm.deepdive.fireman.viewmodel.GameViewModel;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

  private static final String TAG = MainActivity.class.getSimpleName();

  private ActivityMainBinding binding;
  private GameViewModel viewModel;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    binding.terrain.setOnMoveListener((row, col) -> Log.d(TAG, String.format("move at row %1$d, column %2$d", row, col)));
    setContentView(binding.getRoot());
    viewModel = new ViewModelProvider(this).get(GameViewModel.class);
    viewModel.getGame()
        .observe(this, binding.terrain::setGame);
  }

  // TODO: 12/5/2024 implement setUpVIewModel()
}