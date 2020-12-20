package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.Project;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProjectMapper implements RowMapper<Project> {

    @Override
    public Project mapRow(ResultSet rs, int i) throws SQLException {
        return new Project(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("link"),
                rs.getBoolean("is_archived"),
                rs.getLong("user_id")
        );
    }
}
