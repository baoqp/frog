package com.bqp.frog.descriptor;

import com.bqp.frog.annotation.DB;
import com.bqp.frog.annotation.ReturnGeneratedId;
import com.bqp.frog.annotation.Sharding;
import com.bqp.frog.exception.DescriptionException;
import com.bqp.frog.util.Strings;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class MethodDescriptor {

    private final String name;
    private final Class<?> daoClass;
    private final ReturnDescriptor returnDescriptor;
    private final List<ParameterDescriptor> parameterDescriptors;

    private String cachedSQL;

    private MethodDescriptor(
            String name, Class<?> daoClass, ReturnDescriptor returnDescriptor,
            List<ParameterDescriptor> parameterDescriptors) {
        this.name = name;
        this.daoClass = daoClass;
        this.returnDescriptor = returnDescriptor;
        this.parameterDescriptors = Collections.unmodifiableList(parameterDescriptors);
    }

    public static MethodDescriptor create(
            String name, Class<?> daoClass, ReturnDescriptor returnDescriptor,
            List<ParameterDescriptor> parameterDescriptors) {
        return new MethodDescriptor(name, daoClass, returnDescriptor, parameterDescriptors);
    }

    public String getName() {
        return name;
    }

    public Class<?> getDaoClass() {
        return daoClass;
    }

    public boolean isAnnotationPresent(Class<? extends Annotation> annotationType) {
        return getAnnotation(annotationType) != null;
    }


    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return returnDescriptor.getAnnotation(annotationType);
    }

    public Type getReturnType() {
        return returnDescriptor.getType();
    }

    public Class<?> getReturnRawType() {
        return returnDescriptor.getRawType();
    }

    public List<Annotation> getAnnotations() {
        return returnDescriptor.getAnnotations();
    }

    public ReturnDescriptor getReturnDescriptor() {
        return returnDescriptor;
    }

    public List<ParameterDescriptor> getParameterDescriptors() {
        return parameterDescriptors;
    }

    public Sharding getShardingAnno() {
        return getAnnotation(Sharding.class);
    }

    public String getGlobalTable() {
        DB dbAnno = getAnnotation(DB.class);
        if (dbAnno == null) {
            throw new DescriptionException("dao interface expected one @DB " +
                    "annotation but not found");
        }
        String table = null;
        if (Strings.isNotEmpty(dbAnno.table())) {
            table = dbAnno.table();
        }
        return table;
    }

    public boolean isReturnGeneratedId() {
        return isAnnotationPresent(ReturnGeneratedId.class) ||
                (name != null && name.contains("ReturnGeneratedId"));
    }

}
