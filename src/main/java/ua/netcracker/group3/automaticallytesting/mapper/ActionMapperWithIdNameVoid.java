package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdNameVoid;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActionMapperWithIdNameVoid implements RowMapper<ActionDtoWithIdNameVoid> {
    @Override
    public ActionDtoWithIdNameVoid mapRow(ResultSet rs, int i) throws SQLException {
        return new ActionDtoWithIdNameVoid(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getBoolean("is_void")
        );
    }
}
