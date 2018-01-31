package com.bqp.frog.operator;

import java.lang.reflect.Type;

/**
 * @author ash
 */
public interface BindingParameterInvoker {

  /**
   * 获得目标类型
   */
   Type getTargetType();

  /**
   * 执行get方法链
   */
   Object invoke(Object obj);

  /**
   * 获得绑定参数
   */
  BindingParameter getBindingParameter();

}
