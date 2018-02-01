package com.bqp.frog.jdbc;

import com.bqp.frog.util.ListSupplier;

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
