package com.bqp.frog.util;

import java.util.List;

/**
 * @author ash
 */
public interface ListSupplier {

    <T> List<T> get(Class<T> clazz);

}
