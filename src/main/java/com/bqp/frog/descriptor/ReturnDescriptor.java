package com.bqp.frog.descriptor;


import com.bqp.frog.util.Objects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.List;


/**
 * 方法返回描述
 * ReturnDescriptor中的注解是方法和class上的注解
 * @author ash
 */
public class ReturnDescriptor extends TypeWithAnnotationDescriptor {

    private ReturnDescriptor(Type type, List<Annotation> annotations) {
        super(type, annotations);
    }

    public static ReturnDescriptor create(Type type, List<Annotation> annotations) {
        return new ReturnDescriptor(type, annotations);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final ReturnDescriptor other = (ReturnDescriptor) obj;
        return Objects.equal(this.getType(), other.getType())
                && Objects.equal(this.getAnnotations(), other.getAnnotations());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getType(), getAnnotations());
    }

}