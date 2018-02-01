package com.bqp.frog.jdbc;

import com.bqp.frog.util.SetSupplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Set;

/**
 * @author ash
 */
public class SetResultSetExtractor<T> implements ResultSetExtractor<Set<T>> {

    private final SetSupplier setSupplier;
    private final RowMapper<T> rowMapper;

    public SetResultSetExtractor(SetSupplier setSupplier, RowMapper<T> rowMapper) {
        this.setSupplier = setSupplier;
        this.rowMapper = rowMapper;
    }

    @Override
    public Set<T> extractData(ResultSet rs) throws SQLException {
        Set<T> results = setSupplier.get(rowMapper.getMappedClass());
        int rowNum = 0;
        while (rs.next()) {
            results.add(rowMapper.mapRow(rs, rowNum++));
        }
        return results;
    }

}
