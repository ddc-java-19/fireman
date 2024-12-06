package edu.cnm.deepdive.fireman.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import edu.cnm.deepdive.fireman.databinding.ActivityMainBinding;
import edu.cnm.deepdive.fireman.databinding.FragmentGameBinding;
import edu.cnm.deepdive.fireman.viewmodel.GameViewModel;

public class GameFragment extends Fragment {


  private static final String TAG = MainActivity.class.getSimpleName();

  private FragmentGameBinding binding;
  private GameViewModel viewModel;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    }


  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {
    return super.onCreateView(inflater, container, savedInstanceState);
    binding = FragmentGameBinding.inflate(getLayoutInflater());
    binding.getRoot();
    binding.terrain.setOnMoveListener((row, col) -> viewModel.submitMove(row, col));
    setContentView(binding.getRoot());
    viewModel = new ViewModelProvider(this).get(GameViewModel.class);
    viewModel.getGame()
        .observe(this, binding.terrain::setGame);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }
}
