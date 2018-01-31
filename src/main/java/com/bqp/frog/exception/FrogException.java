package com.bqp.frog.exception;

/**
 * @author Bao Qingping
 */
public abstract class FrogException extends RuntimeException {

    public FrogException(String msg) {
        super(msg);
    }

    public FrogException(String msg, Throwable cause) {
        super(msg, cause);
    }

    @Override
    public String getMessage() {
        return buildMessage(super.getMessage(), getCause());
    }

    private String buildMessage(String message, Throwable cause) {
        if (cause != null) {
            StringBuilder sb = new StringBuilder();
            if (message != null) {
                sb.append(message).append("; ");
            }
            sb.append("caused by: ").append(cause.getMessage());
            return sb.toString();
        } else {
            return message;
        }
    }
}
