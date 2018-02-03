package com.bqp.frog.util.reflect;

import com.bqp.frog.util.Tuple;
import com.google.common.reflect.TypeToken;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Bao Qingping
 */
public class TypeTokens {

    @SuppressWarnings("unchecked")
    public static Set<TypeToken<?>> getTypes(TypeToken<?> typeToken) {
        Set<TypeToken<?>> tokens = new HashSet<TypeToken<?>>();
        tokens.add(typeToken);
        TypeToken<?> superclass = null;


        superclass = (TypeToken<?>) Reflection.invokeMethod(typeToken, "getGenericSuperclass",
                new Class[]{}, new Object[]{});


        if (superclass != null) {
            tokens.add(superclass);
            tokens.addAll(getTypes(superclass));
        }
        List<TypeToken<?>> interfaces = null;

        interfaces = (List<TypeToken<?>>) Reflection.invokeMethod(typeToken, "getGenericInterfaces",
                new Class[]{}, new Object[]{});


        for (TypeToken<?> anInterface : interfaces) {
            tokens.add(anInterface);
            tokens.addAll(anInterface.getTypes());
        }
        return tokens;
    }


    public static Tuple<TypeToken, TypeToken> resolveFatherClassTuple(TypeToken<?> typeToken, Class<?> clazz) {
        Set<TypeToken<?>> fathers = getTypes(typeToken);
        TypeToken firstToken = null;
        TypeToken secondToken = null;
        for (TypeToken<?> father : fathers) {
            if (clazz.equals(father.getRawType())) { // 获取clazz指向的父类型的两个泛型参数
                firstToken = father.resolveType(clazz.getTypeParameters()[0]);
                if (Object.class.equals(firstToken.getRawType())) {
                    firstToken = TypeToken.of(Object.class);
                }
                secondToken = father.resolveType(clazz.getTypeParameters()[1]);
                if (Object.class.equals(secondToken.getRawType())) {
                    secondToken = TypeToken.of(Object.class);
                }
            }
        }
        if (firstToken == null || secondToken == null) {
            throw new IllegalStateException();
        }
        return new Tuple(firstToken, secondToken);
    }


    public static TypeToken resolveFatherClass(TypeToken<?> typeToken, Class<?> clazz) {
        Set<TypeToken<?>> fathers = getTypes(typeToken);
        TypeToken token = null;

        for (TypeToken<?> father : fathers) {
            if (clazz.equals(father.getRawType())) {
                token = father.resolveType(clazz.getTypeParameters()[0]);
                if (Object.class.equals(token.getRawType())) {
                    token = TypeToken.of(Object.class);
                }
            }
        }
        if (token == null) {
            throw new IllegalStateException();
        }
        return token;
    }

}
