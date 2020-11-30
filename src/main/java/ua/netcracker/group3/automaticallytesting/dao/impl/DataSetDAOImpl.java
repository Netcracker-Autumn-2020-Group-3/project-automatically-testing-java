package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataSetDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.DataSetDAO;
import ua.netcracker.group3.automaticallytesting.mapper.DataSetMapper;
import ua.netcracker.group3.automaticallytesting.model.DataSet;

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
    private final JdbcTemplate jdbcTemplate;
    private DataSetMapper dataSetMapper;

    @Value("${get.data.set.by.id}")
    private String GET_DATA_SET_BY_NAME;

    @Value("${update.data.set.by.name}")
    private String UPDATE_DATA_SET;

    public DataSetDAOImpl(JdbcTemplate jdbcTemplate,DataSetMapper dataSetMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.dataSetMapper = dataSetMapper;
    }

    @Override
    public DataSet getDataSetById(Integer dataSetId) {
        return jdbcTemplate.queryForObject(GET_DATA_SET_BY_NAME,dataSetMapper,dataSetId);
    }

    @Override
    public void updateDataSet(DataSet editedDataSet) {
        jdbcTemplate.update(UPDATE_DATA_SET,editedDataSet.getName(),editedDataSet.getId());

    }
}
