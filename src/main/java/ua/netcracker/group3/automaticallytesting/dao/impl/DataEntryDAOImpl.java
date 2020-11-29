package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataEntryDAO;
import ua.netcracker.group3.automaticallytesting.mapper.DataEntryMapper;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataEntryDAOImpl implements DataEntryDAO {

    JdbcTemplate jdbcTemplate;
    DataEntryMapper dataEntryMapper;

    @Value("${get.data.entries.by.dataset.id}")
    private String GET_BY_DATA_SET_ID;

    @Autowired
    public DataEntryDAOImpl(JdbcTemplate jdbcTemplate, DataEntryMapper dataEntryMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataEntryMapper = dataEntryMapper;
    }

    @Override
    public void createDataEntry(String dataSetName, String value) {
        jdbcTemplate.update("insert into data_entry (data_set_id, value) values ((select id from data_set where name = ?), ?)", dataSetName, value);
    }

    @Override
    public List<DataEntry> getAllByDataSetId(Long dataSetId) {
         return jdbcTemplate.queryForStream(GET_BY_DATA_SET_ID, dataEntryMapper, dataSetId).collect(Collectors.toList());
    }
}
