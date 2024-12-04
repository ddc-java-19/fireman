package edu.cnm.deepdive.fireman.model.domain;

import com.google.gson.annotations.Expose;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

public class Game {

  @Expose(serialize = false)
  private final String key;
  @Expose(serialize = false)
  private final Instant started;
  @Expose(serialize = false)
  private final Instant finished;
  @Expose(serialize = false)
  private final int score;
  @Expose(serialize = false)
  private final boolean turn;
  @Expose(serialize = false)
  private final Wind wind;
  @Expose(serialize = false)
  private final User arsonist;
  @Expose(serialize = false)
  private final User fireman;
  @Expose(serialize = false)
  private final List<Plot> plots;

  public Game(String key, Instant started, Instant finished, int score, boolean turn, Wind wind,
      User arsonist,
      User fireman, List<Plot> plots) {
    this.key = key;
    this.started = started;
    this.finished = finished;
    this.score = score;
    this.turn = turn;
    this.wind = wind;
    this.arsonist = arsonist;
    this.fireman = fireman;
    this.plots = new LinkedList<>(plots);
  }

  public Game() {
    this.key = null;
    this.started = null;
    this.finished = null;
    this.score = 0;
    this.turn = false;
    this.wind = null;
    this.arsonist = null;
    this.fireman = null;
    this.plots = new LinkedList<>();
  }

  public void arsonistMove(Plot plots) {

  }

  public String getKey() {
    return key;
  }

  public Instant getStarted() {
    return started;
  }

  public Instant getFinished() {
    return finished;
  }

  public int getScore() {
    return score;
  }

  public boolean isTurn() {
    return turn;
  }

  public Wind getWind() {
    return wind;
  }

  public User getArsonist() {
    return arsonist;
  }

  public User getFireman() {
    return fireman;
  }

  public List<Plot> getPlots() {
    return plots;
  }
}
