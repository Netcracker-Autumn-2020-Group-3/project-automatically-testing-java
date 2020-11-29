package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataSetDAO;
import ua.netcracker.group3.automaticallytesting.mapper.DataSetMapper;
import ua.netcracker.group3.automaticallytesting.model.DataSet;

import java.util.List;
import java.util.stream.Collectors;

@PropertySource("classpath:queries/postgres.properties")
@Repository
public class DataSetDAOImpl implements DataSetDAO {

    private JdbcTemplate jdbcTemplate;
    private DataSetMapper dataSetMapper;


    @Value("${get.datasets}")
    private String GET_ALL;

    @Autowired
    public DataSetDAOImpl(JdbcTemplate jdbcTemplate, DataSetMapper dataSetMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSetMapper = dataSetMapper;
    }

    @Override
    public void createDataSet(String name) {
        jdbcTemplate.update("insert into data_set (name) values (?)", name);
    }

    @Override
    public List<DataSet> getAll() {
        return jdbcTemplate.queryForStream(GET_ALL, dataSetMapper).collect(Collectors.toList());
    }
}
