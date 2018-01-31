package com.bqp.frog.operator;


import com.bqp.frog.annotation.Rename;
import com.bqp.frog.descriptor.ParameterDescriptor;
import com.bqp.frog.exception.BindingException;

import java.lang.reflect.Type;
import java.util.*;

/**
 * @author ash
 */
public class DefaultParameterContext implements ParameterContext {

    /**
     * 位置到重命名后的变量名的映射
     */
    private final Map<Integer, String> positionToNameMap = new HashMap<>();

    /**
     * 参数名到参数类型的映射
     */
    private final Map<String, Type> nameToTypeMap = new LinkedHashMap<>();

    private final List<ParameterDescriptor> parameterDescriptors = new ArrayList<>();


    private DefaultParameterContext(List<ParameterDescriptor> parameterDescriptors) {
        for (int i = 0; i < parameterDescriptors.size(); i++) {
            ParameterDescriptor pd = parameterDescriptors.get(i);
            Rename renameAnno = pd.getAnnotation(Rename.class);
            String parameterName = renameAnno != null ?
                    renameAnno.value() : // 优先使用注解中的名字
                    pd.getName();
            nameToTypeMap.put(parameterName, pd.getType());
            int position = pd.getPosition();
            positionToNameMap.put(position, parameterName);
            this.parameterDescriptors.add(pd);
        }
    }

    public static DefaultParameterContext create(List<ParameterDescriptor> parameterDescriptors) {
        return new DefaultParameterContext(parameterDescriptors);
    }

    @Override
    public String getParameterNameByPosition(int position) {
        String name = positionToNameMap.get(position);
        if (name == null) {
            throw new IllegalStateException("parameter name can not be found by position [" + position + "]");
        }
        return name;
    }

    @Override
    public BindingParameterInvoker getBindingParameterInvoker(BindingParameter bindingParameter) {
        String parameterName = bindingParameter.getParameterName();
        Type type = nameToTypeMap.get(parameterName);
        if (type == null) {
            throw new BindingException("Parameter '" + BindingParameter.create(bindingParameter.getParameterName(), "", null) +
                    "' not found, available root parameters are " + transToBindingParameters(nameToTypeMap.keySet()));
        }
        return new BindingParameterInvokerImpl(type, bindingParameter);
    }

    @Override
    public List<ParameterDescriptor> getParameterDescriptors() {
        return parameterDescriptors;
    }


    private Set<BindingParameter> transToBindingParameters(Collection<String> parameterNames) {
        Set<BindingParameter> rs = new LinkedHashSet<BindingParameter>();
        for (String parameterName : parameterNames) {
            rs.add(BindingParameter.create(parameterName, "", null));
        }
        return rs;
    }

}
