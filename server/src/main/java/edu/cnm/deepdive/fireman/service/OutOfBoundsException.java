package edu.cnm.deepdive.fireman.service;

public class OutOfBoundsException extends IllegalArgumentException {

  public OutOfBoundsException() {
  }

  public OutOfBoundsException(String message) {
    super(message);
  }

  public OutOfBoundsException(String message, Throwable cause) {
    super(message, cause);
  }

  public OutOfBoundsException(Throwable cause) {
    super(cause);
  }
}
