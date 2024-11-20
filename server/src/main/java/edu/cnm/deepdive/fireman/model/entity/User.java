package edu.cnm.deepdive.fireman.model.entity;

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
import java.util.UUID;
import java.util.stream.Stream;
import org.hibernate.annotations.CreationTimestamp;

@SuppressWarnings({"JpaDataSourceORMInspection", "DefaultAnnotationParam"})
@Entity
@Table(
    name = "user_profile"
)
public class User {

  @Id
  @GeneratedValue
  @Column(name= "user_profile_id", nullable = false, updatable = false)
  private Long id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Instant created;

  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  private UUID externalKey;

  @Column(nullable = false, updatable = false, unique = true, length = 30)
  private String oauthKey;

  @Column(nullable = false, updatable = true, unique = true, length = 50)
  private String displayName;

  public Long getId() {
    return id;
  }

  public Instant getCreated() {
    return created;
  }

  public UUID getExternalKey() {
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

  @PrePersist
  void computeFieldValues() {
    externalKey = UUID.randomUUID();
  }

}
