package com.bqp.frog.jdbc;


import com.bqp.frog.exception.DataAccessException;
import com.bqp.frog.util.ListSupplier;
import com.bqp.frog.util.SetSupplier;

import javax.sql.DataSource;
import java.util.List;
import java.util.Set;


/**
 * @author ash
 */
public interface JdbcOperations {

    <T> T queryForObject(DataSource ds, BoundSql boundSql, RowMapper<T> rowMapper)
            throws DataAccessException;

    <T> List<T> queryForList(DataSource ds, BoundSql boundSql,
                             ListSupplier listSupplier, RowMapper<T> rowMapper)
            throws DataAccessException;

    <T> Set<T> queryForSet(DataSource ds, BoundSql boundSql,
                           SetSupplier setSupplier, RowMapper<T> rowMapper)
            throws DataAccessException;

    <T> Object queryForArray(DataSource ds, BoundSql boundSql, RowMapper<T> rowMapper)
            throws DataAccessException;

    int update(DataSource ds, BoundSql boundSql)
            throws DataAccessException;

    int update(DataSource ds, BoundSql boundSql, GeneratedKeyHolder holder)
            throws DataAccessException;


}