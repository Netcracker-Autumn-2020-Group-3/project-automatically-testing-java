package ua.netcracker.group3.automaticallytesting.mapper;


import org.springframework.jdbc.core.RowMapper;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompoundWithIdNameMapper implements RowMapper<CompoundDtoWithIdName> {
    @Override
    public CompoundDtoWithIdName mapRow(ResultSet rs, int i) throws SQLException {
        return new CompoundDtoWithIdName(
                rs.getLong("id"),
                rs.getString("name")
        );
    }
}
