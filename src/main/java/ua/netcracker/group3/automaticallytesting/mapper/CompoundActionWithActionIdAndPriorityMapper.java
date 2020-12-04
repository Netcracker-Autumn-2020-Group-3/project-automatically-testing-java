package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.netcracker.group3.automaticallytesting.model.CompoundActionWithActionIdAndPriority;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompoundActionWithActionIdAndPriorityMapper implements RowMapper<CompoundActionWithActionIdAndPriority> {
    @Override
    public CompoundActionWithActionIdAndPriority mapRow(ResultSet rs, int i) throws SQLException {
        return new CompoundActionWithActionIdAndPriority(
                rs.getLong("action_id"),
                rs.getLong("priority")
        );
    }
}
