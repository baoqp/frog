package com.bqp.frog.exception;

public class EmptyObjectException extends FrogException {

  public EmptyObjectException(String msg) {
    super(msg);
  }

  public EmptyObjectException(String msg, Throwable cause) {
    super(msg, cause);
  }

}
