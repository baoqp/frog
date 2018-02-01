package com.bqp.frog.util;

import java.util.Set;

/**
 * @author ash
 */
public interface SetSupplier {

    <T> Set<T> get(Class<T> clazz);

}
