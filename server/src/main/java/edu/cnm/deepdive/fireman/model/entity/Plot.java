package edu.cnm.deepdive.fireman.model.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@SuppressWarnings({"JpaDataSourceORMInspection", "unused", "DefaultAnnotationParam"})
@Entity
public class Plot {

  @Id
  @GeneratedValue
  @Column(name = "plot_id", nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private Long id;

  @Column(nullable = false, updatable = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean charred;

  @Column(nullable = false, updatable = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean wet;

  @Column(nullable = false, updatable = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private boolean burnable;

  @Column(name = "row_number", nullable = false, updatable = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int row;

  @Column(name = "column_number", nullable = false, updatable = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
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
