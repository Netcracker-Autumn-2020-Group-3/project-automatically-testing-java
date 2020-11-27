package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataEntryDAO;

@Repository
public class DataEntryDAOImpl implements DataEntryDAO {

    JdbcTemplate jdbcTemplate;

    @Autowired
    public DataEntryDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void createDataEntry(String dataSetName, String value) {
        jdbcTemplate.update("insert into data_entry (data_set_id, value) values ((select id from data_set where name = ?), ?)", dataSetName, value);
    }
}
