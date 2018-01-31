package com.bqp.frog.exception;

/**
 * @author ash
 */
public class BindingException extends DescriptionException {

  public BindingException(String msg) {
    super(msg);
  }

  public BindingException(String msg, Throwable cause) {
    super(msg, cause);
  }

}