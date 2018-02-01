package com.bqp.frog.jdbc;

import com.bqp.frog.exception.MappingException;
import com.bqp.frog.exception.UnreachablePropertyException;
import com.bqp.frog.invoker.InvokerCache;
import com.bqp.frog.invoker.SetterInvoker;
import com.bqp.frog.invoker.SetterInvokerGroup;
import com.bqp.frog.invoker.SetterInvokerGroupImpl;
import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.jdbc.type.TypeHandlerRegistry;
import com.bqp.frog.util.PropertyTokenizer;
import com.bqp.frog.util.Strings;
import com.bqp.frog.util.reflect.Reflection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 单列或多列组装对象RowMapper
 *
 * @author ash
 */
public class BeanPropertyRowMapper<T> implements RowMapper<T> {

    private Class<T> mappedClass;

    private Map<String, SetterInvoker> invokerMap;

    private Map<String, String> columnToPropertyMap;

    private boolean checkColumn;

    public BeanPropertyRowMapper(Class<T> mappedClass, Map<String, String> propertyToColumnMap, boolean checkColumn) {
        initialize(mappedClass, propertyToColumnMap, checkColumn);
    }

    // propertyToColumnMap 是自定义的属性到列的映射
    protected void initialize(Class<T> mappedClass, Map<String, String> propertyToColumnMap, boolean checkColumn) {
        this.mappedClass = mappedClass;
        this.checkColumn = checkColumn;
        this.columnToPropertyMap = new HashMap<>();
        this.invokerMap = new HashMap<>();

        // 初始化columnToPropertyPathMap
        for (Map.Entry<String, String> entry : propertyToColumnMap.entrySet()) {
            String property = entry.getKey();
            PropertyTokenizer propToken = new PropertyTokenizer(property);
            if (propToken.hasNext()) {
                columnToPropertyMap.put(entry.getValue().toLowerCase(), property);
            }
        }

        // 初始化invokerMap
        List<SetterInvoker> invokers = InvokerCache.getSetterInvokers(mappedClass);
        for (SetterInvoker invoker : invokers) {
            String column = propertyToColumnMap.get(invoker.getName());
            if (column != null) { // 使用配置映射
                invokerMap.put(column.toLowerCase(), invoker);
            } else { // 使用约定映射
                invokerMap.put(invoker.getName().toLowerCase(), invoker);
                String underscoredName = Strings.underscoreName(invoker.getName());
                // 把aBC改为a_b_c(数据库字段名常用形式)
                if (!invoker.getName().toLowerCase().equals(underscoredName)) {
                    invokerMap.put(underscoredName, invoker);
                }
            }
        }
    }

    @Override
    public T mapRow(ResultSet rs, int rowNumber) throws SQLException {
        T mappedObject = Reflection.instantiate(mappedClass);

        ResultSetWrapper rsw = new ResultSetWrapper(rs);
        int columnCount = rsw.getColumnCount();

        for (int index = 1; index <= columnCount; index++) {
            String columnName = rsw.getColumnName(index);
            String lowerCaseColumnName = columnName.toLowerCase();
            String propertyPath = columnToPropertyMap.get(lowerCaseColumnName);
            if (propertyPath != null) { // 使用自定义多级映射
                setValueByPropertyPath(mappedObject, propertyPath, rsw, index, rowNumber);
            } else {
                PropertyTokenizer prop = new PropertyTokenizer(columnName);
                if (prop.hasNext()) { // select 语句中的字段存在多级映射 (如果字段名也是a.b.c这种形式)
                    setValueByPropertyPath(mappedObject, columnName, rsw, index, rowNumber);
                } else { // 单级映射（包含约定和自定义）
                    setValueByProperty(mappedObject, lowerCaseColumnName, rsw, index, rowNumber);
                }
            }
        }
        return mappedObject;
    }

    private void setValueByPropertyPath(Object mappedObject, String propertyPath, ResultSetWrapper rsw,
                                        int index, int rowNumber) throws SQLException {

        SetterInvokerGroup g = null;

        try {
            g = new SetterInvokerGroupImpl(mappedClass, propertyPath);
        } catch (UnreachablePropertyException e) {
            if (checkColumn) {
                throw new MappingException("Unable to map column '" + rsw.getColumnName(index) +
                        "' to property '" + propertyPath + "'");
            }
        }
        if (g != null) {
            TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(g.getTargetType(), rsw.getJdbcType(index));
            Object value = typeHandler.getResult(rsw.getResultSet(), index);

            g.invoke(mappedObject, value);
        }
    }

    private void setValueByProperty(Object mappedObject, String lowerCaseColumnName, ResultSetWrapper rsw,
                                    int index, int rowNumber) throws SQLException {

        SetterInvoker invoker = invokerMap.get(lowerCaseColumnName);
        if (invoker != null) {
            TypeHandler<?> typeHandler = TypeHandlerRegistry.getTypeHandler(invoker.getParameterRawType(),
                    rsw.getJdbcType(index));
            Object value = typeHandler.getResult(rsw.getResultSet(), index);
            invoker.invoke(mappedObject, value);
        } else {
            if (checkColumn) {
                throw new MappingException("Unable to map column '" + rsw.getColumnName(index) +
                        "' to any property of '" + mappedClass + "'");
            }
        }
    }

    @Override
    public Class<T> getMappedClass() {
        return mappedClass;
    }

}