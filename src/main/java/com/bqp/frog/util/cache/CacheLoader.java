package com.bqp.frog.util.cache;

/**
 * @author ash
 */
public interface CacheLoader<K, V> {

  public V load(K key);

}
