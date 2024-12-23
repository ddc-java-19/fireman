package edu.cnm.deepdive.fireman.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import edu.cnm.deepdive.fireman.databinding.FragmentGameBinding;
import edu.cnm.deepdive.fireman.model.domain.Plot;
import edu.cnm.deepdive.fireman.model.domain.PlotState;
import edu.cnm.deepdive.fireman.viewmodel.GameViewModel;
import java.util.Arrays;
import java.util.List;

public class GameFragment extends Fragment {


  private static final String TAG = GameFragment.class.getSimpleName();

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
    super.onCreateView(inflater, container, savedInstanceState);
    binding = FragmentGameBinding.inflate(getLayoutInflater());
    binding.terrain.setOnMoveListener((row, col) -> viewModel.submitMove(row, col));
    binding.viewStats.setOnClickListener((v) ->
        Navigation.findNavController(binding.getRoot())
            .navigate(GameFragmentDirections.navigateToStats()));
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    setupViewModel();
  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }

  public void setupViewModel() {
    viewModel = new ViewModelProvider(requireActivity()).get(GameViewModel.class);
    getLifecycle().addObserver(viewModel);
    LifecycleOwner viewLifecycleOwner = getViewLifecycleOwner();
    viewModel.getGame()
        .observe(viewLifecycleOwner, game -> {
          binding.terrain.setGame(game);
          // TODO: 12/9/2024 get data from game, including plot count for different kinds or plots, including wind; pass to widgets
          String[] stateCountStrings = Arrays.stream(PlotState.values())
              .mapToInt((state) -> countPlotState(game.getPlots(), state))
              .mapToObj((count) -> String.valueOf(count))
              .toArray(String[]::new);
          // TODO: 12/9/2024  use the strings in the above array to populate textViews in the UI.
          binding.compassDirections.setImageLevel(game.getWind().ordinal());
        });
  }

  private int countPlotState(List<Plot> plots, PlotState state) {
    return (int) plots
        .stream()
        .filter((plot) -> plot.getPlotState() == state)
        .count();
  }

}
