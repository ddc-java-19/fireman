package edu.cnm.deepdive.fireman.model;

import java.util.random.RandomGenerator;

public enum Wind {
  NORTH(-1, 0),
  SOUTH(1, 0),
  EAST(0, 1),
  WEST(0, -1);

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
