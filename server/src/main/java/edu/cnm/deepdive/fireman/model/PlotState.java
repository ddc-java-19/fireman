package edu.cnm.deepdive.fireman.model;

public enum PlotState {
  BURNABLE {
    @Override
    public PlotState nextState(Boolean firemanMove) {
      return (firemanMove == null)
          ? this
          : firemanMove
              ? SOAKED
              : ON_FIRE;
    }
  },
  ON_FIRE {
    @Override
    public PlotState nextState(Boolean firemanMove) {
      return (firemanMove == null)
          ? CHARRED
          : firemanMove
              ? UNBURNABLE
              : CHARRED;
    }
  },
  SOAKED {
    @Override
    public PlotState nextState(Boolean firemanMove) {
      return WET;
    }
  },
  WET {
    @Override
    public PlotState nextState(Boolean firemanMove) {
      return (firemanMove == null)
          ? BURNABLE
          : this;
    }
  },
  UNBURNABLE,
  CHARRED;

  // TODO: 11/21/2024 decide how to handle adjacency

  public PlotState nextState(Boolean firemanMove) {
    return this;
  }
}
