package edu.cnm.deepdive.fireman.controller;

import edu.cnm.deepdive.fireman.service.GameOverException;
import edu.cnm.deepdive.fireman.service.InsufficientInformationException;
import edu.cnm.deepdive.fireman.service.OutOfBoundsException;
import edu.cnm.deepdive.fireman.service.OutOfTurnException;
import java.util.NoSuchElementException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Configuration
public class ExceptionMapping {

  @ExceptionHandler(InsufficientInformationException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Move does not contain expected content.")
  public void insufficientInformation() {
  }

  @ExceptionHandler(OutOfBoundsException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Move row or column out of bounds.")
  public void outOfBounds() {
  }

  @ExceptionHandler(OutOfTurnException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Move attempted while not in user's turn.")
  public void outOfTurn() {
  }

  @ExceptionHandler(GameOverException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Move attempted on a finished game.")
  public void gameOver() {
  }

  @ExceptionHandler(NoSuchElementException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Requested resource does not exist.")
  public void noSuchElement() {
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid request content.")
  public void illegalArgument() {
  }

  @ExceptionHandler(IllegalStateException.class)
  @ResponseStatus(value = HttpStatus.CONFLICT, reason = "Game is not in a valid state for request.")
  public void illegalState() {
  }

}
