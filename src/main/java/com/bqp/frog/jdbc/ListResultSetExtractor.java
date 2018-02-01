package com.bqp.frog.jdbc;

import com.bqp.frog.util.ListSupplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ash
 */
public class ListResultSetExtractor<T> implements ResultSetExtractor<List<T>> {

    private final ListSupplier listSupplier;
    private final RowMapper<T> rowMapper;

    public ListResultSetExtractor(ListSupplier listSupplier, RowMapper<T> rowMapper) {
        this.listSupplier = listSupplier;
        this.rowMapper = rowMapper;
    }

    @Override
    public List<T> extractData(ResultSet rs) throws SQLException {
        List<T> results = listSupplier.get(rowMapper.getMappedClass());
        int rowNum = 0;
        while (rs.next()) {
            results.add(rowMapper.mapRow(rs, rowNum++));
        }
        return results;
    }

}