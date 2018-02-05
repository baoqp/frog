package com.bqp.frog.exception;

/**
 * 预加载异常
 *
 * @author ash
 */
public class InitializationException extends FrogException {

    public InitializationException(String msg) {
        super(msg);
    }

    public InitializationException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
