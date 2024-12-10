package edu.cnm.deepdive.fireman.model.domain;

import com.google.gson.annotations.Expose;

public enum Wind {
  @Expose NORTH(-1, 0),
  @Expose SOUTH(1, 0),
  @Expose EAST(0, 1),
  @Expose WEST(0, -1);

  private final int rowOffset;
  private final int columnOffset;

  Wind(int rowOffset, int columnOffset) {
    this.rowOffset = rowOffset;
    this.columnOffset = columnOffset;
  }


  public int getRowOffset() {
    return rowOffset;
  }

  public int getColumnOffset() {
    return columnOffset;
  }
}
