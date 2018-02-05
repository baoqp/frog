package com.bqp.frog.jdbc;


import com.bqp.frog.annotation.SQL;
import com.bqp.frog.exception.DataAccessException;
import com.bqp.frog.jdbc.type.TypeHandler;
import com.bqp.frog.util.ListSupplier;
import com.bqp.frog.util.SetSupplier;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;
import java.util.Set;


/**
 * @author ash
 */
public class JdbcTemplate implements JdbcOperations {

    @Override
    public <T> T queryForObject(DataSource dataSource, BoundSql boundSql, RowMapper<T> rowMapper)
            throws DataAccessException {
        return executeQuery(dataSource, boundSql, new ObjectResultSetExtractor<T>(rowMapper));
    }

    @Override
    public <T> List<T> queryForList(DataSource dataSource, BoundSql boundSql,
                                    ListSupplier listSupplier, RowMapper<T> rowMapper)
            throws DataAccessException {

        return executeQuery(dataSource, boundSql, new ListResultSetExtractor<T>(listSupplier, rowMapper));
    }

    @Override
    public <T> Set<T> queryForSet(DataSource dataSource, BoundSql boundSql,
                                  SetSupplier setSupplier, RowMapper<T> rowMapper)
            throws DataAccessException {

        return executeQuery(dataSource, boundSql, new SetResultSetExtractor<T>(setSupplier, rowMapper));
    }

    @Override
    public <T> Object queryForArray(DataSource dataSource, BoundSql boundSql, RowMapper<T> rowMapper)
            throws DataAccessException {

        return executeQuery(dataSource, boundSql, new ArrayResultSetExtractor<T>(rowMapper));
    }

    @Override
    public int update(DataSource dataSource, BoundSql boundSql)
            throws DataAccessException {

        return update(dataSource, boundSql, null);
    }

    @Override
    public int update(DataSource dataSource, BoundSql boundSql, GeneratedKeyHolder holder) throws DataAccessException {
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("获取连接出错");
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = boundSql.getSql();
        try {
            boolean needGenerateKey = holder != null;
            ps = needGenerateKey ?
                    conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS) : // 生成自增key
                    conn.prepareStatement(sql); // 不生成自增key
            setValues(ps, boundSql);

            int r = ps.executeUpdate();
            if (needGenerateKey) { // 生成自增key
                rs = ps.getGeneratedKeys();
                if (!rs.next()) {
                    throw new DataAccessException("Unable to retrieve the generated key. " +
                            "Check that the table has an identity column enabled.");
                }
                Number key = holder.getTypeHandler().getResult(rs, 1);
                holder.setKey(key);
            }
            return r;
        } catch (SQLException e) {
            closeResultSet(rs);
            rs = null;
            closeStatement(ps);
            ps = null;
            throw new DataAccessException(e.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            try {
                conn.close();
            } catch (SQLException e) {

            }
            conn = null;
        }
    }


    private <T> T executeQuery(DataSource dataSource, BoundSql boundSql, ResultSetExtractor<T> rse)
            throws DataAccessException {

        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        } catch (SQLException e) {
            throw new DataAccessException("获取连接出错");
        }

        PreparedStatement ps = null;
        ResultSet rs = null;
        String sql = boundSql.getSql();
        try {
            ps = conn.prepareStatement(sql);
            setValues(ps, boundSql);
            rs = ps.executeQuery();
            return rse.extractData(rs);
        } catch (SQLException e) {
            closeResultSet(rs);
            rs = null;
            closeStatement(ps);
            ps = null;
            throw new DataAccessException(e.getMessage());
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
            try {
                conn.close();
            } catch (SQLException e) {

            }
            conn = null;
        }
    }


    private void setValues(PreparedStatement ps, BoundSql boundSql) throws SQLException {
        List<Object> args = boundSql.getArgs();
        List<TypeHandler<?>> typeHandlers = boundSql.getTypeHandlers();
        for (int i = 0; i < args.size(); i++) {
            TypeHandler typeHandler;
            typeHandler = typeHandlers.get(i);
            Object value = args.get(i);
            typeHandler.setParameter(ps, i + 1, value);
        }
    }


    /**
     * 关闭语句
     *
     * @param stmt
     */
    private void closeStatement(Statement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭结果集
     *
     * @param rs
     */
    private void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
