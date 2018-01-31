package com.bqp.frog.operator;

import com.bqp.frog.exception.BindingException;
import com.bqp.frog.invoker.GetterInvoker;
import com.bqp.frog.invoker.InvokerCache;
import com.bqp.frog.util.PropertyTokenizer;
import com.google.common.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bao Qingping
 */
public class BindingParameterInvokerImpl implements BindingParameterInvoker {

    private final Type targetType;
    private final BindingParameter bindingParameter;
    private final List<GetterInvoker> invokers;

    public BindingParameterInvokerImpl(Type originalType, BindingParameter bindingParameter) {

        this.bindingParameter = bindingParameter;
        invokers = new ArrayList<>();
        Type currentType = originalType;
        Class<?> rawType = TypeToken.of(currentType).getRawType();
        PropertyTokenizer prop = new PropertyTokenizer(bindingParameter.getPropertyPath());
        while (prop.hasCurrent()) {
            String propertyName = prop.getName();
            GetterInvoker invoker = InvokerCache.getGetterInvoker(rawType, propertyName);
            invokers.add(invoker);
            currentType = invoker.getReturnType();
            rawType = TypeToken.of(currentType).getRawType();
            prop = prop.next();
        }
        targetType = currentType;
    }


    @Override
    public Type getTargetType() {
        return targetType;
    }

    @Override
    public Object invoke(Object obj) {
        Object r = obj;
        int size = invokers.size();
        for (int i = 0; i < size; i++) {
            if (r == null) {
                NestedProperty np = new NestedProperty();
                for (int j = 0; j < i; j++) {
                    np.append(invokers.get(j).getName());
                }
                BindingParameter bp = BindingParameter.create(bindingParameter.getParameterName(), np.getNestedProperty(), null);
                throw new BindingException("Parameter '" + bp + "' is null");
            }
            r = invokers.get(i).invoke(r);
        }
        return r;
    }

    @Override
    public BindingParameter getBindingParameter() {
        return bindingParameter;
    }


    public static class NestedProperty {

        private StringBuilder nestedProperty = new StringBuilder();
        private int num = 0;

        public void append(String property) {
            if (num++ == 0) {
                nestedProperty.append(property);
            } else {
                nestedProperty.append("." + property);
            }
        }

        public String getNestedProperty() {
            return nestedProperty.toString();
        }

    }
}



