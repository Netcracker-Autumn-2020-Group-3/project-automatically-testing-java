package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataEntryDAO;
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

    @Value("${update.or.insert.data.entry}")
    private String INSERT_DATA_ENTRY_FOR_UPDATE;

    @Value("${update.or.insert.data.entry.default}")
    private String INSERT_DATA_ENTRY_FOR_UPDATE_DEFAULT;

    @Value("${delete.data.entry.by.id}")
    private String DELETE_DATA_ENTRY_BY_ID;


    @Autowired
    public DataEntryDAOImpl(JdbcTemplate jdbcTemplate,DataEntryMapper dataEntryMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.dataEntryMapper = dataEntryMapper;
    }

    @Override
    public List<DataEntry> getDataEntryByDataSetName(Integer dataSetId) {
        return jdbcTemplate.queryForStream(GET_DATA_ENTRY_FOR_EDIT,dataEntryMapper,dataSetId).collect(Collectors.toList());
    }

    @Override
    public void updateDataEntry(List<DataEntry> dataEntryList) {
        for (DataEntry de: dataEntryList) {
            if (de.getId() == null){
                jdbcTemplate.update(INSERT_DATA_ENTRY_FOR_UPDATE_DEFAULT,de.getData_set_id(),de.getValue());
            }else{
                jdbcTemplate.update(INSERT_DATA_ENTRY_FOR_UPDATE,de.getId(),de.getData_set_id(),de.getValue());
            }
        }
    }

    @Override
    public void deleteDataEntryValueById(Integer dataEntryId) {
        jdbcTemplate.update(DELETE_DATA_ENTRY_BY_ID, dataEntryId);
    }


    @Override
    public void createDataEntry(String dataSetName, String value) {
        jdbcTemplate.update("insert into data_entry (data_set_id, value) values ((select id from data_set where name = ?), ?)", dataSetName, value);
    }
}
