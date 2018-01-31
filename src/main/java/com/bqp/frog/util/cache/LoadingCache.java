package com.bqp.frog.util.cache;

/**
 * @author ash
 */
public interface LoadingCache<K, V> {

  public V get(K key);

}