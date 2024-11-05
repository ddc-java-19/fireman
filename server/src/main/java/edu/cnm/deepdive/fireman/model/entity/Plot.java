package edu.cnm.deepdive.fireman.model.entity;


@Entity
public class Plot {

  @Id
  @Column(name = "plot_id")
  private Long id;

  @Column
  private boolean charred;

  @Column
  private boolean wet;

  @Column
  private boolean burnable;

  @Column
  private long wind;

  @Column
  private int row;

  @Column
  private int column;

  public boolean isCharred() {
    return charred;
  }

  public void setCharred(boolean charred) {
    this.charred = charred;
  }

  public boolean isWet() {
    return wet;
  }

  public void setWet(boolean wet) {
    this.wet = wet;
  }

  public boolean isBurnable() {
    return burnable;
  }

  public void setBurnable(boolean burnable) {
    this.burnable = burnable;
  }

  public long getWind() {
    return wind;
  }

  public void setWind(long wind) {
    this.wind = wind;
  }

  public int getRow() {
    return row;
  }

  public void setRow(int row) {
    this.row = row;
  }

  public int getColumn() {
    return column;
  }

  public void setColumn(int column) {
    this.column = column;
  }
}
