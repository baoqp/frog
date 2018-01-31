package com.bqp.frog.operator;

import com.bqp.frog.descriptor.ParameterDescriptor;

import java.util.List;

/**
 * @author ash
 */
public interface ParameterContext {

  /**
   * 根据参数位置获得参数名
   */
   String getParameterNameByPosition(int position);

  /**
   * 获得绑定参数调用器
   */
   BindingParameterInvoker getBindingParameterInvoker(BindingParameter bindingParameter);

  /**
   * 获得参数描述
   */
   List<ParameterDescriptor> getParameterDescriptors();


}