package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class DataEntryMapper  implements RowMapper<DataEntry> {
    @Override
    public DataEntry mapRow(ResultSet resultSet, int i) throws SQLException {
        return DataEntry.builder().id(resultSet.getLong("id"))
                .value(resultSet.getString("value")).build();
    }
}
