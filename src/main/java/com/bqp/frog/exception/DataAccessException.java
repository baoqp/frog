package com.bqp.frog.exception;

public  class DataAccessException extends FrogException {

  public DataAccessException(String msg) {
    super(msg);
  }

  public DataAccessException(String msg, Throwable cause) {
    super(msg, cause);
  }

}