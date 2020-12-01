package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.DataSetDAO;
import ua.netcracker.group3.automaticallytesting.mapper.DataSetMapper;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import org.springframework.jdbc.core.RowMapper;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;



import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@PropertySource("classpath:queries/postgres.properties")
@Repository
public class DataSetDAOImpl implements DataSetDAO {

    private final JdbcTemplate jdbcTemplate;

    private DataSetMapper dataSetMapper;

    @Value("${get.data.set.by.id}")
    private String GET_DATA_SET_BY_NAME;

    @Value("${update.data.set.by.name}")
    private String UPDATE_DATA_SET;

    @Value("${get.datasets}")
    private String GET_ALL;

    @Autowired
    public DataSetDAOImpl(JdbcTemplate jdbcTemplate, DataSetMapper dataSetMapper){
        this.jdbcTemplate = jdbcTemplate;
        this.dataSetMapper = dataSetMapper;
    }

    @Override
    public long createDataSet(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "insert into data_set (name) values (?) returning id";
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, name);
            return ps;
            }, keyHolder);
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    @Override
    public List<DataSet> getAll() {
        return jdbcTemplate.queryForStream(GET_ALL, dataSetMapper).collect(Collectors.toList());
    }

    @Override
    public DataSet getDataSetById(Integer dataSetId) {
        return jdbcTemplate.queryForObject(GET_DATA_SET_BY_NAME,dataSetMapper,dataSetId);
    }

    @Override
    public void updateDataSet(DataSet editedDataSet) {
        jdbcTemplate.update(UPDATE_DATA_SET,editedDataSet.getName(),editedDataSet.getId());
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
