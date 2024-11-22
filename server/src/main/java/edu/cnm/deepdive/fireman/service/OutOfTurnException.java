package edu.cnm.deepdive.fireman.service;

public class OutOfTurnException extends IllegalArgumentException {

  public OutOfTurnException() {
  }

  public OutOfTurnException(String message) {
    super(message);
  }

  public OutOfTurnException(String message, Throwable cause) {
    super(message, cause);
  }

  public OutOfTurnException(Throwable cause) {
    super(cause);
  }
}
