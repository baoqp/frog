package com.bqp.frog.invoker;

import com.bqp.frog.util.PropertyTokenizer;
import com.bqp.frog.util.reflect.Reflection;

/**
 * @author Clinton Begin
 * @author ash
 */
public class MetaObject {

    private Object originalObject;

    private Class<?> originalClass;

    private MetaObject(Object object) {
        this.originalObject = object;
        this.originalClass = object.getClass();
    }

    public static MetaObject forObject(Object object) {
        if (object == null) {
            return SystemMetaObject.NULL_META_OBJECT;
        } else {
            return new MetaObject(object);
        }
    }

    public Object getOriginalObject() {
        return originalObject;
    }

    private Object getValue(String propertyName) {
        GetterInvoker invoker = InvokerCache.getGetterInvoker(originalClass, propertyName);
        return invoker.invoke(originalObject);
    }

    public void setValue(String propertyPath, Object value) {
        PropertyTokenizer prop = new PropertyTokenizer(propertyPath);
        if (prop.hasNext()) {
            MetaObject metaValue = metaObjectForProperty(prop.getName());
            if (metaValue == SystemMetaObject.NULL_META_OBJECT) { // 如果某个属性是对象，且当前是null的，需要实例化这个对象属性
                if (value == null && prop.getChildren() != null) {
                    // don't instantiate child path if value is null
                    return;
                } else {
                    //
                    SetterInvoker invoker = InvokerCache.getSetterInvoker(originalClass, prop.getName());
                    Class<?> clazz = invoker.getParameterRawType();
                    Object newObject = Reflection.instantiate(clazz);
                    metaValue = MetaObject.forObject(newObject);
                    invoker.invoke(originalObject, newObject);
                }
            }
            metaValue.setValue(prop.getChildren(), value); // 递归
        } else {
            SetterInvoker invoker = InvokerCache.getSetterInvoker(originalClass, propertyPath);
            invoker.invoke(originalObject, value);
        }
    }

    public MetaObject metaObjectForProperty(String name) {
        Object value = getValue(name);
        return MetaObject.forObject(value);
    }


}
