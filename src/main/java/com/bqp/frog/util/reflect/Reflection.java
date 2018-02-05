package com.bqp.frog.util.reflect;

import com.bqp.frog.exception.ReflectionException;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author ash
 */
public class Reflection {

    public static <T> T instantiate(Class<T> clazz) throws BeanInstantiationException {
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "specified class is an interface");
        }
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            throw new BeanInstantiationException(clazz, "Is it an abstract class?", e);
        } catch (IllegalAccessException e) {
            throw new BeanInstantiationException(clazz, "Is the constructor accessible?", e);
        }
    }

    public static <T> T instantiateClass(Class<T> clazz) throws BeanInstantiationException {
        if (clazz.isInterface()) {
            throw new BeanInstantiationException(clazz, "Specified class is an interface");
        }
        try {
            return instantiateClass(clazz.getDeclaredConstructor());
        } catch (NoSuchMethodException ex) {
            throw new BeanInstantiationException(clazz, "No default constructor found", ex);
        }
    }

    public static <T> T instantiateClass(Constructor<T> ctor, Object... args) throws BeanInstantiationException {
        try {
            makeAccessible(ctor);
            return ctor.newInstance(args);
        } catch (InstantiationException ex) {
            throw new BeanInstantiationException(ctor.getDeclaringClass(),
                    "Is it an abstract class?", ex);
        } catch (IllegalAccessException ex) {
            throw new BeanInstantiationException(ctor.getDeclaringClass(),
                    "Is the constructor accessible?", ex);
        } catch (IllegalArgumentException ex) {
            throw new BeanInstantiationException(ctor.getDeclaringClass(),
                    "Illegal arguments for constructor", ex);
        } catch (InvocationTargetException ex) {
            throw new BeanInstantiationException(ctor.getDeclaringClass(),
                    "Constructor threw exception", ex.getTargetException());
        }
    }

    public static <T> T newProxy(
            Class<T> interfaceType, InvocationHandler handler) {
        if (!interfaceType.isInterface()) {
            throw new IllegalArgumentException("expected an interface to proxy, but " + interfaceType);
        }
        Object object = Proxy.newProxyInstance(
                interfaceType.getClassLoader(),
                new Class<?>[]{interfaceType},
                handler);
        return interfaceType.cast(object);
    }

    public static void makeAccessible(Constructor<?> ctor) {
        if ((!Modifier.isPublic(ctor.getModifiers()) || !Modifier.isPublic(ctor.getDeclaringClass().getModifiers())) &&
                !ctor.isAccessible()) {
            ctor.setAccessible(true);
        }
    }

    public static Set<Annotation> getAnnotations(Class<?> clazz) {
        Set<Annotation> annos = new HashSet<Annotation>();
        getAnnotations(clazz, annos);
        return annos;
    }

    // 递归获取所有的注解
    static void getAnnotations(Class<?> clazz, Set<Annotation> annos) {
        if (clazz == null) {
            return;
        }
        annos.addAll(Arrays.asList(clazz.getDeclaredAnnotations()));
        for (Class<?> parent : clazz.getInterfaces()) {
            getAnnotations(parent, annos);
        }
        getAnnotations(clazz.getSuperclass(), annos);
    }


    public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] args) throws ReflectionException {
        Class<?> clazz = obj.getClass();
        Method method = null;
        Throwable throwable = null;
        while (clazz != Object.class) {
            try {
                method = clazz.getDeclaredMethod(methodName, parameterTypes);
                if (method != null && !isAbstract(method)) { // method 不为抽象的
                    break;
                } else {
                    method = null;
                }
            } catch (NoSuchMethodException NSE) {
                throwable = NSE;
            }
            clazz = clazz.getSuperclass();
        }
        if (method != null) {
            method.setAccessible(true);
            try {
                return method.invoke(obj, args);

            } catch (Exception e) {
                throwable = e;
            }
        }

        throw new ReflectionException("调用方法 " + methodName + " 出错", throwable);
    }

    private static boolean isAbstract(Method m) {
        return (m.getModifiers() & Modifier.ABSTRACT) > 0;
    }


}