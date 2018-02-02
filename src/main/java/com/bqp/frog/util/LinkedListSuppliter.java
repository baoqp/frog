package com.bqp.frog.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author ash
 */
public class LinkedListSuppliter implements ListSupplier {

    @Override
    public <T> List<T> get(Class<T> clazz) {
        return new LinkedList<T>();
    }

}
