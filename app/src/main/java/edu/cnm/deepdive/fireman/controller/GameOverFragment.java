package edu.cnm.deepdive.fireman.controller;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import edu.cnm.deepdive.fireman.R;

public class GameOverFragment extends DialogFragment {

  private String message;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    message = GameOverFragmentArgs.fromBundle(getArguments()).getMessage();
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
    return new AlertDialog.Builder(requireContext())
        .setTitle(R.string.game_over)
        .setIcon(R.drawable.hydrant)
        .setMessage(message)
        .setNeutralButton(android.R.string.ok, (dlg, wh) -> {})
        .create();
  }
}
