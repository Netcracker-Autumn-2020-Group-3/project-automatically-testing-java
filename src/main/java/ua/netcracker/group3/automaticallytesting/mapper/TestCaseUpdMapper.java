package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.TestCaseUpd;


import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class TestCaseUpdMapper implements RowMapper<TestCaseUpd> {
    @Override
    public TestCaseUpd mapRow(ResultSet rs, int i) throws SQLException {
                return TestCaseUpd.builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name")).build();

    }
}


/*
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
 */

