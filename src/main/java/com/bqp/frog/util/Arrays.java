package com.bqp.frog.util;

import java.lang.reflect.Array;
import java.util.List;

/**
 * @author ash
 */
public class Arrays {

    public static <T> Object toArray(List<T> list, Class<T> clazz) {
        Object array = Array.newInstance(clazz, list.size());
        for (int i = 0; i < list.size(); i++) {
            Array.set(array, i, list.get(i));
        }
        return array;
    }

}
