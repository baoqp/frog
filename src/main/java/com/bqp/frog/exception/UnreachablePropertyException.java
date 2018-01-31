package com.bqp.frog.exception;

/**
 * @author Bao Qingping
 */
public class UnreachablePropertyException extends FrogException {

    public UnreachablePropertyException(String msg) {
        super(msg);
    }

    public UnreachablePropertyException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
