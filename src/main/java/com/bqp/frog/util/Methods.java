package com.bqp.frog.util;

import com.bqp.frog.descriptor.MethodDescriptor;
import com.bqp.frog.descriptor.ParameterDescriptor;
import com.bqp.frog.descriptor.ReturnDescriptor;
import com.bqp.frog.util.reflect.ParamNameResolver;
import com.bqp.frog.util.reflect.Reflection;
import com.google.common.reflect.TypeToken;
import sun.net.www.content.text.Generic;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.*;

import static com.bqp.frog.util.Arrays.*;

/**
 * @author Bao Qingping
 */
public class Methods {

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

        // method 所在 class 的注解也要保存
        for (Annotation a : method.getAnnotations()) {
            mas.add(a);
        }

        for (Annotation a : Reflection.getAnnotations(daoClass)) {
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
            pds.add(ParameterDescriptor.create(i, type, java.util.Arrays.asList(pas), name));
        }

        return MethodDescriptor.create(method.getName(), daoClass, rd, pds);
    }


    public static List<Method> listMethods(Class<?> clazz) {
        Method[] allMethods = clazz.getMethods();
        List<Method> methods = new ArrayList<Method>();
        for (Method method : allMethods) {
            if (!isDefault(method)) {
                methods.add(method);
            }
        }
        return methods;
    }

    // java8: 接口中的默认方法
    private static boolean isDefault(Method m) {
        // Default methods are public non-abstract instance methods
        // declared in an interface.
        return ((m.getModifiers() & (Modifier.ABSTRACT | Modifier.PUBLIC | Modifier.STATIC)) ==
                Modifier.PUBLIC) && m.getDeclaringClass().isInterface();
    }



}
