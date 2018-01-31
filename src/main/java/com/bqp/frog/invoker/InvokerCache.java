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

        private final List<GetterInvoker> getterInvokers;

        public InvokerInfo(Class<?> clazz) throws Exception {

            Map<String, GetterInvoker> gim = new HashMap<>();

            List<GetterInvoker> gis = new ArrayList<GetterInvoker>();

            for (PropertyMeta pm : BeanUtil.fetchPropertyMetas(clazz)) {
                String name = pm.getName();
                Method readMethod = pm.getReadMethod();
                Method writeMethod = pm.getWriteMethod();

                GetterInvoker fgi = new GetterInvokerImpl(name, readMethod);
                gim.put(name, fgi);
                gis.add(fgi);

            }
            getterInvokerMap = Collections.unmodifiableMap(gim);
            getterInvokers = Collections.unmodifiableList(gis);
        }

        GetterInvoker getGetterInvoker(String propertyName) {
            return getterInvokerMap.get(propertyName);
        }

        private List<GetterInvoker> getGetterInvokers() {
            return getterInvokers;
        }

        private static boolean isBoolean(Class<?> clazz) {
            return boolean.class.equals(clazz) || Boolean.class.equals(clazz);
        }

    }

}
