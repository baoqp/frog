package com.bqp.frog.invoker;

import com.bqp.frog.exception.UnreachablePropertyException;
import com.bqp.frog.util.PropertyTokenizer;

/**
 * 用于多级属性映射
 *
 * @author Bao Qingping
 */
public class SetterInvokerGroupImpl implements SetterInvokerGroup {

    private final Class<?> originalType;
    private final String propertyPath;
    private final Class<?> targetType;

    public SetterInvokerGroupImpl(Class<?> originalType, String propertyPath) {
        this.originalType = originalType;
        this.propertyPath = propertyPath;
        PropertyTokenizer prop = new PropertyTokenizer(propertyPath);
        if (!prop.hasNext()) {
            throw new IllegalStateException("property path '" + propertyPath + "' error");
        }
        Class<?> currentType = originalType;
        while (prop.hasCurrent()) {
            String propertyName = prop.getName();
            SetterInvoker setter = InvokerCache.getSetterInvoker(currentType, propertyName);
            if (prop.hasNext()) { // 后续还有属性则需检测set方法
                GetterInvoker getter = InvokerCache.getGetterInvoker(currentType, propertyName);
                if (!setter.getParameterType().equals(getter.getReturnType())) { // 有set方法，但get方法不对，抛出异常
                    throw new UnreachablePropertyException("Inconsistent setter/getter type for property named '" +
                            propertyName + "' in '" + currentType + "'");
                }
            }
            currentType = setter.getParameterRawType();
            prop = prop.next();
        }
        targetType = currentType;
    }

    @Override
    public Class<?> getOriginalType() {
        return originalType;
    }

    @Override
    public Class<?> getTargetType() {
        return targetType;
    }

    @Override
    public void invoke(Object obj, Object value) {
        MetaObject mo = MetaObject.forObject(obj);
        mo.setValue(propertyPath, value);
    }

}
