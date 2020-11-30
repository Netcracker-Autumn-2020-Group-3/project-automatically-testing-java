package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataSetDAO;
import ua.netcracker.group3.automaticallytesting.model.DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class DataSetDAOImpl implements DataSetDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DataSetDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createDataSet(String name) {
        jdbcTemplate.update("insert into data_set (name) values (?)", name);
    }

    @Override
    public List<DataSet> getAllDataSet() {
        return jdbcTemplate.query("select id, name from data_set", new RowMapper<DataSet>() {
            @Override
            public DataSet mapRow(ResultSet resultSet, int i) throws SQLException {
                return new DataSet(resultSet.getLong(1), resultSet.getString(2));
            }
        });
    }
}
