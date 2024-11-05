import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import java.time.Instant;
import java.util.UUID;

@Entity(
    tableName = "game",
    indices = {
        @Index(value = {"score"}),
        @Index(value = {"time"})
    },
    foreignKeys = {
        @ForeignKey(entity = User.class,
            parentColumns = {"user_id"}, childColumns = {"user_id"},
            onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,
            parentColumns = {"user_id"}, childColumns = {"firefighter_id"},
            onDelete = ForeignKey.CASCADE),
        @ForeignKey(entity = User.class,
            parentColumns = {"user_id"}, childColumns = {"arsonist_id"},
            onDelete = ForeignKey.CASCADE)
    }
)
public class Game {

  @PrimaryKey(autoGenerate = true)
  @ColumnInfo(name = "game_id")
  private long gameId;

  @ColumnInfo(name = "time", index = true)
  private long time;

  @ColumnInfo(name = "score", index = true)
  private int score;

  @ColumnInfo(name = "is_finished")
  private Instant isFinished;

  @ColumnInfo(name = "turn")
  private boolean turn;

  @ColumnInfo(name = "external_key", unique = true)
  private UUID externalKey;

  @ColumnInfo(name = "user_id", index = true)
  private long userId;

  @ColumnInfo(name = "firefighter_id", index = true)
  private long firefighterId;

  @ColumnInfo(name = "arsonist_id", index = true)
  private long arsonistId;

  // Getters and Setters
  public long getGameId() {
    return gameId;
  }

  public void setGameId(long gameId) {
    this.gameId = gameId;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public Instant getIsFinished() {
    return isFinished;
  }

  public void setIsFinished(Instant isFinished) {
    this.isFinished = isFinished;
  }

  public boolean isTurn() {
    return turn;
  }

  public void setTurn(boolean turn) {
    this.turn = turn;
  }

  public UUID getExternalKey() {
    return externalKey;
  }

  public void setExternalKey(UUID externalKey) {
    this.externalKey = externalKey;
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public long getFirefighterId() {
    return firefighterId;
  }

  public void setFirefighterId(long firefighterId) {
    this.firefighterId = firefighterId;
  }

  public long getArsonistId() {
    return arsonistId;
  }

  public void setArsonistId(long arsonistId) {
    this.arsonistId = arsonistId;
  }
}