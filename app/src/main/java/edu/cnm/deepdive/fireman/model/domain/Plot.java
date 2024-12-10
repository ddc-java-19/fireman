package edu.cnm.deepdive.fireman.model.domain;

import com.google.gson.annotations.Expose;
import java.time.Instant;
import java.util.List;

public class Plot {


  @Expose(serialize = false)
  private final PlotState plotState;
  @Expose(serialize = false)
  private final int row;
  @Expose(serialize = false)
  private final int column;

  public Plot(PlotState plotState,int row, int column) {

    this.plotState = plotState;
    this.row = row;
    this.column = column;
  }

//  public final boolean isSolved() {
//    plots.get(plotState)
//    if(plots.get(!plotState.ON_FIRE.equals(true)))
//  }

  public PlotState getPlotState() {
    return plotState;
  }

  public int getRow() {
    return row;
  }

  public int getColumn() {
    return column;
  }
}
