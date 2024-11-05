package edu.cnm.deepdive.appstarter.model.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(
    tableName = "move",
    foreignKeys = {
        @ForeignKey(entity = Game.class,
            parentColumns = {"game_id"}, childColumns = {"game_id"},
            onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,
            parentColumns = {"user_id"}, childColumns = {"user_id"},
            onDelete = ForeignKey.CASCADE)
    },
    indices = {
        @Index(value = {"game_id"}),
        @Index(value = {"user_id"})
    }
)
public class Move {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "move_id")
  private long moveId;

  @ColumnInfo(name = "row")
  private int row;

  @ColumnInfo(name = "column")
  private int column;

  @ColumnInfo(name = "game_id", index = true)
  private long gameId;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  // Getters and Setters
  public long getMoveId() {
    return moveId;
  }

  public void setMoveId(long moveId) {
    this.moveId = moveId;
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

  public long getGameId() {
    return gameId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }
}