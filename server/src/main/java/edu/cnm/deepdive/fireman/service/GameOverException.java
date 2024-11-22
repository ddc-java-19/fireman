package edu.cnm.deepdive.fireman.service;

public class GameOverException extends IllegalStateException{

  public GameOverException() {
  }

  public GameOverException(String message) {
    super(message);
  }

  public GameOverException(String message, Throwable cause) {
    super(message, cause);
  }

  public GameOverException(Throwable cause) {
    super(cause);
  }
}
