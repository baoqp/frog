package com.bqp.frog.util;

import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.descriptor.ParameterDescriptor;
import com.bqp.frog.descriptor.ReturnDescriptor;
import com.bqp.frog.util.reflect.ParamNameResolver;
import com.google.common.reflect.TypeToken;
import sun.net.www.content.text.Generic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author Bao Qingping
 */
public class Methods {

    // 递归获取所有的注解
    public static Set<Annotation> getAnnotations(Class<?> clazz) {
        Set<Annotation> annos = new HashSet<Annotation>();
        getAnnotations(clazz, annos);
        return annos;
    }

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

    private static final TypeToken<?> genericTypeToken = TypeToken.of(Generic.class);

    static Type resolveType(Type type, TypeToken<?> daoTypeToken) {
        return daoTypeToken.isSubtypeOf(genericTypeToken) ?
                daoTypeToken.resolveType(type).getType() :
                type;
    }

    private static String[] getParameterNames(Method method, boolean isUseActualParamName) {
        String[] names = new String[method.getGenericParameterTypes().length];
        for (int i = 0; i < names.length; i++) {
            String name = null;

            if (isUseActualParamName) {
                name = ParamNameResolver.getActualParamName(method, i);
            }
            if (name == null) {
                name = String.valueOf(i + 1);
            }
            names[i] = name;
        }
        return names;
    }


    public static MethodDescriptor getMethodDescriptor(Class<?> daoClass, Method method, boolean isUseActualParamName) {
        List<Annotation> mas = new LinkedList<Annotation>();

        for (Annotation a : method.getAnnotations()) {
            mas.add(a);
        }

        for (Annotation a : getAnnotations(daoClass)) {
            mas.add(a);
        }

        TypeToken<?> daoTypeToken = TypeToken.of(daoClass);
        Type returnType = resolveType(method.getGenericReturnType(), daoTypeToken);

        ReturnDescriptor rd = ReturnDescriptor.create(returnType, mas);
        List<ParameterDescriptor> pds = new LinkedList<ParameterDescriptor>();
        Type[] genericParameterTypes = method.getGenericParameterTypes();
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        String[] names = getParameterNames(method, isUseActualParamName);
        for (int i = 0; i < genericParameterTypes.length; i++) {
            Type type = resolveType(genericParameterTypes[i], daoTypeToken);
            Annotation[] pas = parameterAnnotations[i];
            String name = names[i];
            pds.add(ParameterDescriptor.create(i, type, Arrays.asList(pas), name));
        }

        return MethodDescriptor.create(method.getName(), daoClass, rd, pds);
    }


}
