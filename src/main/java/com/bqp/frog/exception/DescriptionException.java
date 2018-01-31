package com.bqp.frog.exception;

import com.bqp.frog.exception.FrogException;

public class DescriptionException extends FrogException {

    public DescriptionException(String msg) {
        super(msg);
    }

    public DescriptionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
