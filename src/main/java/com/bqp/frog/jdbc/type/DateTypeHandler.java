/*
 * Copyright 2014 mango.jfaster.org
 *
 * The Mango Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package com.bqp.frog.jdbc.type;

import com.bqp.frog.jdbc.JdbcType;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author Clinton Begin
 * @author ash
 */
public class DateTypeHandler extends BaseTypeHandler<Date> {

  @Override
  public void setNonNullParameter(PreparedStatement ps, int index, Date parameter, JdbcType jdbcType)
      throws SQLException {
    ps.setTimestamp(index, new Timestamp((parameter).getTime()));
  }

  @Override
  public Date getNullableResult(ResultSet rs, int index)
      throws SQLException {
    Timestamp sqlTimestamp = rs.getTimestamp(index);
    if (sqlTimestamp != null) {
      return new Date(sqlTimestamp.getTime());
    }
    return null;
  }

  @Override
  public JdbcType getJdbcType() {
    return JdbcType.TIMESTAMP;
  }
}
