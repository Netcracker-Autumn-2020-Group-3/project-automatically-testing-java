package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DataSetMapper implements RowMapper<DataSet> {

    /**
     * @param resultSet
     * @param i
     * @return
     * @throws SQLException
     */
    @Override
    public DataSet mapRow(ResultSet resultSet, int i) throws SQLException {
        return DataSet.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
