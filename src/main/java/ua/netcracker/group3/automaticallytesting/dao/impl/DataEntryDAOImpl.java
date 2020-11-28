package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataEntryDAO;
import ua.netcracker.group3.automaticallytesting.mapper.DataEntryMapper;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.service.ServiceImpl.DataEntryServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DataEntryDAOImpl implements DataEntryDAO {

    private final JdbcTemplate jdbcTemplate;
    private DataEntryMapper dataEntryMapper;

    @Value("${get.data.entry.by.data.set.id}")
    private String GET_DATA_ENTRY_FOR_EDIT;

    @Autowired
    public DataEntryDAOImpl(JdbcTemplate jdbcTemplate,DataEntryMapper dataEntryMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.dataEntryMapper = dataEntryMapper;
    }

    @Override
    public List<DataEntry> getDataEntryByDataSetName(Integer dataSetId) {
        return jdbcTemplate.queryForStream(GET_DATA_ENTRY_FOR_EDIT,dataEntryMapper,dataSetId).collect(Collectors.toList());
    }
}
