package com.bqp.frog.jdbc.type;

import java.sql.SQLException;

/**
 * @author ash
 */
public class TypeException extends SQLException {

    public TypeException(String msg) {
        super(msg);
    }

    public TypeException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
