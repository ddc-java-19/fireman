package edu.cnm.deepdive.fireman.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import edu.cnm.deepdive.fireman.model.PlotState;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@SuppressWarnings({"JpaDataSourceORMInspection", "unused", "DefaultAnnotationParam"})
@Entity
public class Plot {

  @Id
  @GeneratedValue
  @Column(name = "plot_id", nullable = false, updatable = false)
  @JsonIgnore
  private Long id;

  @Column(nullable = false, updatable = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private PlotState plotState;

  @Column(name = "row_number", nullable = false, updatable = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int row;

  @Column(name = "column_number", nullable = false, updatable = true)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int column;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "game_id", nullable = false, updatable = false)
  @JsonIgnore
  private Game game;

  public Long getId() {
    return id;
  }

  public PlotState getPlotState() {
    return plotState;
  }

  public void setPlotState(PlotState plotState) {
    this.plotState = plotState;
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

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
}
