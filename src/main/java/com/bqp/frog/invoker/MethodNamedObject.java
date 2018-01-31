package com.bqp.frog.invoker;

import java.lang.reflect.Method;

/**
 * @author ash
 */
public abstract class MethodNamedObject implements NamedObject {

  protected String name;
  protected Method method;

  protected MethodNamedObject(String name, Method method) {
    this.name = name;
    this.method = method;
    handleMethod(method);
  }

  @Override
  public String getName() {
    return name;
  }

  private void handleMethod(Method method) {
    method.setAccessible(true); // 提高反射速度
  }

}
