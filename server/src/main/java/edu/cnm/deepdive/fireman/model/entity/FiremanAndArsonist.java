package edu.cnm.deepdive.fireman.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.LinkedList;
import java.util.List;

public class FiremanAndArsonist {


  @OneToMany(
      mappedBy = "fireman", fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      orphanRemoval = false
  )
  @OrderBy("started DESC")
  private final List<Game> firemanGames = new LinkedList<>();

  @OneToMany(
      mappedBy = "arsonist", fetch = FetchType.LAZY,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
      orphanRemoval = false
  )
  @OrderBy("started DESC")
  private final List<Game> arsonistGames = new LinkedList<>();

  public List<Game> getFiremanGames() {
    return firemanGames;
  }

  public List<Game> getArsonistGames() {
    return arsonistGames;
  }
}
