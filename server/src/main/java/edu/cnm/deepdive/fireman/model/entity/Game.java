package edu.cnm.deepdive.fireman.model.entity;

import edu.cnm.deepdive.fireman.model.Wind;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.Instant;
import java.util.UUID;
import org.hibernate.annotations.CreationTimestamp;

@SuppressWarnings({"JpaDataSourceORMInspection", "unused", "DefaultAnnotationParam",
    "SpellCheckingInspection"})
@Entity
@Table(
    indexes = {
        @Index(columnList = "score, finished")
    }
)
public class Game {

  @Id
  @GeneratedValue
  @Column(name = "game_id", nullable = false, updatable = false)
  private long id;

  @Column(nullable = false, updatable = false, unique = true, columnDefinition = "UUID")
  private UUID externalKey;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Instant started;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = true, updatable = true)
  private Instant finished;

  @Column(nullable = false, updatable = true)
  private int score;

  @Column(nullable = false, updatable = true)
  private boolean firemansTurn;

  @Enumerated(EnumType.ORDINAL)
  @Column(nullable = false, updatable = true)
  private Wind wind;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "fireman_id", nullable = true, updatable = true)
  private User fireman;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "arsonist_id", nullable = true, updatable = true)
  private User arsonist;

  public long getId() {
    return id;
  }

  public UUID getExternalKey() {
    return externalKey;
  }

  public Instant getStarted() {
    return started;
  }

  public Instant getFinished() {
    return finished;
  }

  public void setFinished(Instant finished) {
    this.finished = finished;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public boolean isFiremansTurn() {
    return firemansTurn;
  }

  public void setFiremansTurn(boolean firemansTurn) {
    this.firemansTurn = firemansTurn;
  }

  public User getFireman() {
    return fireman;
  }

  public void setFireman(User firefighter) {
    this.fireman = firefighter;
  }

  public User getArsonist() {
    return arsonist;
  }

  public void setArsonist(User arsonist) {
    this.arsonist = arsonist;
  }

  public boolean isCompleted() {
    return finished != null;
  }

  public Wind getWind() {

    return wind;
  }

  public void setWind(Wind wind) {
    this.wind = wind;
  }

  @PrePersist
  void computeFieldValues() {
    externalKey = UUID.randomUUID();
  }

}