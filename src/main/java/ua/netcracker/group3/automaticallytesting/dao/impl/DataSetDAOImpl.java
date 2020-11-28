package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataSetDAO;

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
}
