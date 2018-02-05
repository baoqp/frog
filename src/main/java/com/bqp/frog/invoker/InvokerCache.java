package com.bqp.frog.invoker;

import com.bqp.frog.exception.UncheckedException;
import com.bqp.frog.exception.UnreachablePropertyException;
import com.bqp.frog.util.bean.BeanUtil;
import com.bqp.frog.util.bean.PropertyMeta;
import com.bqp.frog.util.cache.CacheLoader;
import com.bqp.frog.util.cache.DoubleCheckCache;
import com.bqp.frog.util.cache.LoadingCache;

import java.lang.reflect.Method;
import java.util.*;

public class InvokerCache {


    public static GetterInvoker getNullableGetterInvoker(Class<?> clazz, String propertyName) {
        return cache.get(clazz).getGetterInvoker(propertyName);
    }

    public static GetterInvoker getGetterInvoker(Class<?> clazz, String propertyName) {
        GetterInvoker invoker = getNullableGetterInvoker(clazz, propertyName);
        if (invoker == null) {
            throw new UnreachablePropertyException("There is no getter/setter for property named '" + propertyName + "' in '" + clazz + "'");
        }
        return invoker;
    }

    public static List<GetterInvoker> getGetterInvokers(Class<?> clazz) {
        return cache.get(clazz).getGetterInvokers();
    }


    public static SetterInvoker getNullableSetterInvoker(Class<?> clazz, String propertyName) {
        return cache.get(clazz).getSetterInvoker(propertyName);
    }

    public static SetterInvoker getSetterInvoker(Class<?> clazz, String propertyName) {
        SetterInvoker invoker = cache.get(clazz).getSetterInvoker(propertyName);
        if (invoker == null) {
            throw new UnreachablePropertyException("There is no getter/setter for property named '" + propertyName + "' in '" + clazz + "'");
        }
        return invoker;
    }

    public static List<SetterInvoker> getSetterInvokers(Class<?> clazz) {
        return cache.get(clazz).getSetterInvokers();
    }


    private final static LoadingCache<Class<?>, InvokerInfo> cache = new DoubleCheckCache<>(
            new CacheLoader<Class<?>, InvokerInfo>() {
                public InvokerInfo load(Class<?> clazz) {
                    try {
                        return new InvokerInfo(clazz);
                    } catch (Exception e) {
                        throw new UncheckedException(e.getMessage(), e);
                    }
                }
            });

    private static class InvokerInfo {

        private final Map<String, GetterInvoker> getterInvokerMap;
        private final Map<String, SetterInvoker> setterInvokerMap;
        private final List<GetterInvoker> getterInvokers;
        private final List<SetterInvoker> setterInvokers;

        public InvokerInfo(Class<?> clazz) throws Exception {
            Map<String, GetterInvoker> gim = new HashMap<String, GetterInvoker>();
            Map<String, SetterInvoker> sim = new HashMap<String, SetterInvoker>();
            List<GetterInvoker> gis = new ArrayList<GetterInvoker>();
            List<SetterInvoker> sis = new ArrayList<SetterInvoker>();

            for (PropertyMeta pm : BeanUtil.fetchPropertyMetas(clazz)) {
                String name = pm.getName();
                Method readMethod = pm.getReadMethod();
                Method writeMethod = pm.getWriteMethod();
                GetterInvokerImpl gi = new GetterInvokerImpl(name, readMethod);
                gim.put(name, gi);
                gis.add(gi);
                SetterInvokerImpl si = new SetterInvokerImpl(name, writeMethod);
                sim.put(name, si);
                sis.add(si);
            }
            getterInvokerMap = Collections.unmodifiableMap(gim);
            setterInvokerMap = Collections.unmodifiableMap(sim);
            getterInvokers = Collections.unmodifiableList(gis);
            setterInvokers = Collections.unmodifiableList(sis);
        }

        GetterInvoker getGetterInvoker(String propertyName) {
            return getterInvokerMap.get(propertyName);
        }

        SetterInvoker getSetterInvoker(String propertyName) {
            return setterInvokerMap.get(propertyName);
        }

        private List<GetterInvoker> getGetterInvokers() {
            return getterInvokers;
        }

        private List<SetterInvoker> getSetterInvokers() {
            return setterInvokers;
        }

        private static boolean isBoolean(Class<?> clazz) {
            return boolean.class.equals(clazz) || Boolean.class.equals(clazz);
        }

    }

}
