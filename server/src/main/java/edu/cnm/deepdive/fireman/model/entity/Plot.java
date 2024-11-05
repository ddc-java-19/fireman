package edu.cnm.deepdive.fireman.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Plot {

  @Id
  @GeneratedValue
  @Column(name = "plot_id", nullable = false, updatable = false)
  private Long id;

  @Column(nullable = false, updatable = true)
  private boolean charred;

  @Column(nullable = false, updatable = true)
  private boolean wet;

  @Column(nullable = false, updatable = true)
  private boolean burnable;

  @Column(name = "row_number", nullable = false, updatable = true)
  private int row;

  @Column(name = "column_number", nullable = false, updatable = true)
  private int column;

  public Long getId() {
    return id;
  }

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
