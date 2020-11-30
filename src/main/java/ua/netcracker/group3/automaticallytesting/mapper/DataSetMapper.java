package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.DataSet;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class DataSetMapper implements RowMapper<DataSet> {

    @Override
    public DataSet mapRow(ResultSet resultSet, int i) throws SQLException {
        return DataSet.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .build();
    }
}
