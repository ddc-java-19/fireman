package edu.cnm.deepdive.fireman.controller;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import dagger.hilt.android.AndroidEntryPoint;
import edu.cnm.deepdive.fireman.databinding.FragmentStatsBinding;
import edu.cnm.deepdive.fireman.model.domain.User;
import java.util.List;

@AndroidEntryPoint
public class StatsFragment extends Fragment {

  private FragmentStatsBinding binding;
//  private CompletedGameViewModel viewModel;
  private List<User> users;
  private User user;

  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    binding = FragmentStatsBinding.inflate(inflater, container, false);
    binding.viewGame.setOnClickListener((v) ->
        Navigation.findNavController(binding.getRoot())
            .navigate(StatsFragmentDirections.navigateToGame()));
    return binding.getRoot();
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

  @Override
  public void onDestroyView() {
    binding = null;
    super.onDestroyView();
  }
//
//  @Override
//  public void onStartTrackingTouch() {
//    // Do nothing; empty implementation.
//  }
//
//  @Override
//  public void onStopTrackingTouch() {
//    // Do nothing; empty implementation.
//  }
//
//  @Override
//  public void onNothingSelected(AdapterView<?> parent) {
//    //ignore this event.
//  }
//

}