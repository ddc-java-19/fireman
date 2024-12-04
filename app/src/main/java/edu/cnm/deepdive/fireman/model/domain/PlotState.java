package edu.cnm.deepdive.fireman.model.domain;

import com.google.gson.annotations.Expose;

public enum PlotState {
  @Expose BURNABLE,
  @Expose ON_FIRE,
  @Expose SOAKED,
  @Expose WET,
  @Expose UNBURNABLE,
  @Expose CHARRED;

}
