package com.bqp.frog.util;

/**
 * 工具类，表示元组
 * @author Bao Qingping
 */
public class Tuple<K, T> {

    private final K first;

    private final T second;

    public Tuple(K first, T second) {
        this.first = first;
        this.second = second;
    }

    public K getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }
}
