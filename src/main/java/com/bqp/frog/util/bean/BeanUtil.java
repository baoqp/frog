package com.bqp.frog.util.bean;

import com.bqp.frog.exception.UncheckedException;
import com.bqp.frog.util.Strings;
import com.bqp.frog.util.cache.CacheLoader;
import com.bqp.frog.util.cache.DoubleCheckCache;
import com.bqp.frog.util.cache.LoadingCache;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;


public class BeanUtil {

    private static final int MISS_FLAG = -1;

    private final static LoadingCache<Class<?>, List<PropertyMeta>> cache =
            new DoubleCheckCache<>(
                    new CacheLoader<Class<?>, List<PropertyMeta>>() {
                        public List<PropertyMeta> load(Class<?> clazz) {
                            try {
                                BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
                                List<Field> fields = fetchField(clazz);
                                TreeMap<Integer, PropertyMeta> metaMap;
                                metaMap = new TreeMap<>();
                                PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
                                int missIndex = fields.size();
                                for (PropertyDescriptor pd : pds) {
                                    Method readMethod = pd.getReadMethod();
                                    Method writeMethod = pd.getWriteMethod();
                                    if (readMethod != null && writeMethod != null) {
                                        String name = pd.getName();
                                        Type type = readMethod.getGenericReturnType(); // 和writeMethod的type相同
                                        Field field = tryGetField(readMethod.getDeclaringClass(), name);
                                        if (isBoolean(pd.getPropertyType()) && field == null) {
                                            String bname = "is" + Strings.firstLetterToUpperCase(name);
                                            field = tryGetField(clazz, bname);
                                            if (field != null) {
                                                name = bname;  // 使用isXxYy替换xxYy
                                            }
                                        }
                                        PropertyMeta meta = new PropertyMeta(name, type, readMethod, writeMethod,
                                                methodAnnos(readMethod), methodAnnos(writeMethod), fieldAnnos(field));
                                        int index = indexOfFields(field, fields);
                                        if (index == MISS_FLAG) {
                                            index = missIndex;
                                            missIndex++;
                                        }
                                        metaMap.put(index, meta);
                                    }
                                }
                                return transToList(metaMap);
                            } catch (Exception e) {
                                throw new UncheckedException(e.getMessage(), e);
                            }
                        }
                    });

    public static List<PropertyMeta> fetchPropertyMetas(Class<?> clazz) {
        return cache.get(clazz);
    }

    static List<Field> fetchField(Class<?> clazz) {
        List<Field> fields = new LinkedList<>();
        fillFields(clazz, fields);
        return fields;
    }

    private static void fillFields(Class<?> clazz, List<Field> fields) {
        if (Object.class.equals(clazz)) {
            return;
        }
        fillFields(clazz.getSuperclass(), fields);
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
    }

    private static boolean isBoolean(Class<?> clazz) {
        return boolean.class.equals(clazz) || Boolean.class.equals(clazz);
    }

    private static int indexOfFields(Field field, List<Field> fields) {
        if (field != null) {
            for (int i = 0; i < fields.size(); i++) {
                if (field.equals(fields.get(i))) {
                    return i;
                }
            }
        }
        return MISS_FLAG;
    }


    private static Field tryGetField(Class<?> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (Exception e) {
            // ignore
            return null;
        }
    }

    //获取方法上的注解
    private static Set<Annotation> methodAnnos(Method m) {
        Set<Annotation> annos = new HashSet<Annotation>();
        for (Annotation anno : m.getAnnotations()) {
            annos.add(anno);
        }
        return annos;
    }

    // 获取field上的注解
    private static Set<Annotation> fieldAnnos(Field f) {
        Set<Annotation> annos = new HashSet<>();
        if (f != null) {
            for (Annotation anno : f.getAnnotations()) {
                annos.add(anno);
            }
        }
        return annos;
    }

    private static List<PropertyMeta> transToList(TreeMap<Integer, PropertyMeta> metaMap) {
        List<PropertyMeta> metas = new ArrayList<PropertyMeta>();
        for (Integer key : metaMap.keySet()) {
            metas.add(metaMap.get(key));
        }
        return Collections.unmodifiableList(metas);
    }

}
