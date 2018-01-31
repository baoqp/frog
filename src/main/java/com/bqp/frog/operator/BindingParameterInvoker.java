package com.bqp.frog.operator;

import java.lang.reflect.Type;

/**
 * @author ash
 */
public interface BindingParameterInvoker {

  /**
   * 获得目标类型
   */
  public Type getTargetType();

  /**
   * 执行get方法链
   */
  public Object invoke(Object obj);

  /**
   * 获得绑定参数
   */
  public BindingParameter getBindingParameter();

}
