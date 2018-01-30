package com.bqp.frog.operator;

import com.bqp.frog.jdbc.JdbcType;
import com.bqp.frog.util.Objects;
import com.bqp.frog.util.Strings;

/**
 * @author ash
 */
public class BindingParameter {

    private final String parameterName;

    private final String propertyPath;

    private final JdbcType jdbcType;

    public BindingParameter(String parameterName, String propertyPath, JdbcType jdbcType) {
        this.parameterName = parameterName;
        this.propertyPath = propertyPath;
        this.jdbcType = jdbcType;
    }

    public static BindingParameter create(String parameterName, String propertyPath, JdbcType jdbcType) {
        return new BindingParameter(parameterName, propertyPath, jdbcType);
    }

    public BindingParameter rightShift() {
        String newPropertyPath = Strings.isNotEmpty(propertyPath) ?
                parameterName + "." + propertyPath :
                parameterName;
        return BindingParameter.create("", newPropertyPath, jdbcType);
    }

    public String getParameterName() {
        return parameterName;
    }

    public String getPropertyPath() {
        return propertyPath;
    }

    public JdbcType getJdbcType() {
        return jdbcType;
    }

    public String getFullName() {
        return Strings.getFullName(parameterName, propertyPath);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final BindingParameter other = (BindingParameter) obj;
        return Objects.equal(this.getParameterName(), other.getParameterName())
                && Objects.equal(this.getPropertyPath(), other.getPropertyPath())
                && Objects.equal(this.getJdbcType(), other.getJdbcType());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getParameterName(), getPropertyPath(), getJdbcType());
    }

    @Override
    public String toString() {
        return getFullName();
    }

}
