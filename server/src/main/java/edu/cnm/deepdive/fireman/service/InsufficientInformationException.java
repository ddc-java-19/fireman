package edu.cnm.deepdive.fireman.service;

public class InsufficientInformationException extends IllegalArgumentException {

  public InsufficientInformationException() {
  }

  public InsufficientInformationException(String message) {
    super(message);
  }

  public InsufficientInformationException(String message, Throwable cause) {
    super(message, cause);
  }

  public InsufficientInformationException(Throwable cause) {
    super(cause);
  }
}
