
package com.bqp.frog.jdbc.type;

import com.bqp.frog.jdbc.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Date;

/**
 * @author Clinton Begin
 * @author ash
 */
public class TimeOnlyTypeHandler extends BaseTypeHandler<Date> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Date parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setTime(index, new Time(parameter.getTime()));
  }

  @Override
  public Date getNullableResult(ResultSet rs, int index)
      throws SQLException {
    Time sqlTime = rs.getTime(index);
    if (sqlTime != null) {
      return new Date(sqlTime.getTime());
    }
    return null;
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.TIME;
  }
}
