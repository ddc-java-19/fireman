package edu.cnm.deepdive.fireman.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;
import org.hibernate.annotations.CreationTimestamp;

@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam"})
@Entity
@Table(
    name = "user_profile"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"key", "displayName", "created"})
public class User {

  @Id
  @GeneratedValue
  @Column(name= "user_profile_id", nullable = false, updatable = false)
  @JsonIgnore
  private Long id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  @JsonProperty(access = Access.READ_ONLY)
  private Instant created;

  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  @JsonProperty(value ="key", access = Access.READ_ONLY)
  private UUID externalKey;

  @Column(nullable = false, updatable = false, unique = true, length = 30)
  @JsonIgnore
  private String oauthKey;

  @Column(nullable = false, updatable = true, unique = true, length = 50)
  @JsonProperty(access = Access.READ_ONLY)
  private String displayName;

  @OneToMany(
      mappedBy = "fireman", fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      orphanRemoval = false
  )
  @OrderBy("started DESC")
  @JsonIgnore
  private final List<Game> firemanGames = new LinkedList<>();

  @OneToMany(
      mappedBy = "arsonist", fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      orphanRemoval = false
  )
  @OrderBy("started DESC")
  @JsonIgnore
  private final List<Game> arsonistGames = new LinkedList<>();

  public Long getId() {
    return id;
  }

  public Instant getCreated() {
    return created;
  }

  public UUID getExternalKey(UUID key) {
    return externalKey;
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

  public List<Game> getFiremanGames() {
    return firemanGames;
  }

  public List<Game> getArsonistGames() {
    return arsonistGames;
  }

  @JsonIgnore
  public List<Game> getGames() {
    return Stream.concat(firemanGames.stream(), arsonistGames.stream())
        .sorted(Comparator.comparing(Game::getStarted).reversed())
        .toList();
  }

  @PrePersist
  void computeFieldValues() {
    externalKey = UUID.randomUUID();
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public boolean equals(Object obj) {
    boolean result;
    if(this == obj) {
      result = true;
    } else if(obj instanceof User other) {
      result = id != null && id.equals(other.id);
    } else{
      result = false;
    }

    return result;
  }
}
