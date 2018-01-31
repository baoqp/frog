package com.bqp.frog.util.bean;

import com.bqp.frog.util.Objects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Set;

public class PropertyMeta {

    private final String name;

    private final Type type;

    private final Method readMethod;

    private final Method writeMethod;

    private final Set<Annotation> readMethodAnnos;

    private final Set<Annotation> writeMethodAnnos;

    private final Set<Annotation> propertyAnnos;

    public PropertyMeta(
            String name, Type type, Method readMethod, Method writeMethod,
            Set<Annotation> readMethodAnnos, Set<Annotation> writeMethodAnnos,
            Set<Annotation> propertyAnnos) {
        this.name = name;
        this.type = type;
        this.readMethod = readMethod;
        this.writeMethod = writeMethod;
        this.readMethodAnnos = readMethodAnnos;
        this.writeMethodAnnos = writeMethodAnnos;
        this.propertyAnnos = propertyAnnos;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public Method getReadMethod() {
        return readMethod;
    }

    public Method getWriteMethod() {
        return writeMethod;
    }


    public <T extends Annotation> T getReadMethodAnno(Class<T> annotationType) {
        for (Annotation anno : readMethodAnnos) {
            if (annotationType.isInstance(anno)) {
                return annotationType.cast(anno);
            }
        }
        return null;
    }


    public <T extends Annotation> T getWriteMethodAnno(Class<T> annotationType) {
        for (Annotation anno : writeMethodAnnos) {
            if (annotationType.isInstance(anno)) {
                return annotationType.cast(anno);
            }
        }
        return null;
    }


    public <T extends Annotation> T getPropertyAnno(Class<T> annotationType) {
        for (Annotation anno : propertyAnnos) {
            if (annotationType.isInstance(anno)) {
                return annotationType.cast(anno);
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyMeta)) {
            return false;
        }
        PropertyMeta other = (PropertyMeta) obj;
        return Objects.equal(this.name, other.name) &&
                Objects.equal(this.type, other.type) &&
                Objects.equal(this.readMethod, other.readMethod) &&
                Objects.equal(this.writeMethod, other.writeMethod) &&
                Objects.equal(this.readMethodAnnos, other.readMethodAnnos) &&
                Objects.equal(this.writeMethodAnnos, other.writeMethodAnnos) &&
                Objects.equal(this.propertyAnnos, other.propertyAnnos);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name, type, readMethod, writeMethod, readMethodAnnos, writeMethodAnnos, propertyAnnos);
    }
}
