package com.bqp.frog.jdbc;

import com.bqp.frog.jdbc.type.TypeHandler;

/**
 * 只支持自增主键
 * @author ash
 */
public class GeneratedKeyHolder {

  private Number key;

  private final TypeHandler<? extends Number> typeHandler;

  public GeneratedKeyHolder(TypeHandler<? extends Number> typeHandler) {
    this.typeHandler = typeHandler;
  }

  public Number getKey() {
    return key;
  }

  public void setKey(Number key) {
    this.key = key;
  }

  public TypeHandler<? extends Number> getTypeHandler() {
    return typeHandler;
  }
}
