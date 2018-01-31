package com.bqp.frog.util.reflect;

/**
 * 运行时实例化类异常
 *
 * @author ash
 */
public class BeanInstantiationException extends RuntimeException {

    private Class beanClass;

    public BeanInstantiationException(Class beanClass, String msg) {
        this(beanClass, msg, null);
    }

    public BeanInstantiationException(Class beanClass, String msg, Throwable cause) {
        super("Could not instantiate bean class [" + beanClass.getName() + "]: " + msg, cause);
        this.beanClass = beanClass;
    }

    public Class getBeanClass() {
        return beanClass;
    }

}