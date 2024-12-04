package edu.cnm.deepdive.fireman.model.domain;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Index;
import com.google.gson.annotations.Expose;
import java.time.Instant;
import java.util.Objects;

public class User {

  @Expose
  private String displayName = "";

  @Expose
  private Instant created = Instant.now();

  @Expose
  private String key;

  public void setDisplayName(@NonNull String displayName) {
    this.displayName = displayName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setCreated(@NonNull Instant created) {
    this.created = created;
  }

  public Instant getCreated() {
    return created;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  @NonNull
  @Override
  public String toString() {
    return displayName;
  }

  @Override
  public boolean equals(@Nullable Object obj) {
    boolean result;
    if (this == obj){
      result = true;
    }else if (obj instanceof User other){
      result = this.key != null && this.key.equals(other.key);
    }else {
      result = false;
    }
    return result;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(key);
  }

}
