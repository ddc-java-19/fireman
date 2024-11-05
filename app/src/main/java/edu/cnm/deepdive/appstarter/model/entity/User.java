package edu.cnm.deepdive.appstarter.model.entity;


import androidx.room.Entity;
import androidx.room.Index;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Entity(tableName = "user_id", indices = {
    @Index(value = "oauth_key", unique = true),
    @Index(value = "display_name", unique = true),
}
    )
public class User {

  private Instant created;


    private UUID externalKey;


    private String oauthKey;


    private String displayName;

    private final List<Game> games = new LinkedList<>();

    public Long getId() {
      return id;
    }

    public UUID getExternalKey() {
      return externalKey;
    }

    public Instant getCreated() {
      return created;
    }

    public String getOauthKey() {
      return oauthKey;
    }

    public void setOauthKey(String oauthKey) {
      this.oauthKey = oauthKey;
    }

    public String getDisplayName() {
      return displayName;
    }

    public void setDisplayName(String displayName) {
      this.displayName = displayName;
    }

    public List<Game> getGames() {
      return games;
    }
  }
  }