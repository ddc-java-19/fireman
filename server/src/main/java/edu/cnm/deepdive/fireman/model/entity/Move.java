package edu.cnm.deepdive.fireman.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@SuppressWarnings({"JpaDataSourceORMInspection", "unused", "DefaultAnnotationParam"})
@Entity
public class Move {

  @Id
  @GeneratedValue
  @Column(name = "move_id", nullable = false, updatable = false)
  @JsonIgnore
  private long id;

  @Column(nullable = false, updatable = false)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private int moveNumber;

  @Column(name = "row_number", nullable = true, updatable = true)
  private Integer row;

  @Column(name = "column_number", nullable = true, updatable = true)
  private Integer column;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "game_id", nullable = false, updatable = false)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private Game game;

  @ManyToOne(optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  @JsonProperty(access = JsonProperty.Access.READ_ONLY)
  private User user;

  public long getId() {
    return id;
  }

  public int getMoveNumber() {
    return moveNumber;
  }

  public void setMoveNumber(int moveNumber) {
    this.moveNumber = moveNumber;
  }

  public Integer getRow() {
    return row;
  }

  public void setRow(Integer row) {
    this.row = row;
  }

  public Integer getColumn() {
    return column;
  }

  public void setColumn(Integer column) {
    this.column = column;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}