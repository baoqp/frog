package com.bqp.frog.jdbc;

import com.bqp.frog.util.ListSupplier;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ash
 */
public class ArrayListSupplier implements ListSupplier {

  @Override
  public <T> List<T> get(Class<T> clazz) {
    return new ArrayList<T>();
  }

}