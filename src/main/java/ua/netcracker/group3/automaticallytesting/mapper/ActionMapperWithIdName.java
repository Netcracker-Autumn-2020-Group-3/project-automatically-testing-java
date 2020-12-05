package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdName;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionMapperWithIdName implements RowMapper<ActionDtoWithIdName> {
    @Override
    public ActionDtoWithIdName mapRow(ResultSet rs, int i) throws SQLException {
        return new ActionDtoWithIdName(
                rs.getLong(1),
                rs.getString(2)
        );
    }
}
