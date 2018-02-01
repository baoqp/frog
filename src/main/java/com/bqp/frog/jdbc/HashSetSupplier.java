package com.bqp.frog.jdbc;

import com.bqp.frog.util.SetSupplier;

import java.util.HashSet;
import java.util.Set;

/**
 * @author ash
 */
public class HashSetSupplier implements SetSupplier {

  @Override
  public <T> Set<T> get(Class<T> clazz) {
    return new HashSet<T>();
  }

}