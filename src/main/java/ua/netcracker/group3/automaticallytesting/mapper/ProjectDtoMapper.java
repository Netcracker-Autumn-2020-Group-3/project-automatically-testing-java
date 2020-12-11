package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.ProjectDto;
import ua.netcracker.group3.automaticallytesting.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ProjectDtoMapper implements RowMapper<ProjectDto> {

    @Override
    public ProjectDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return ProjectDto.builder()
                .id(resultSet.getLong("id"))
                .link(resultSet.getString("link"))
                .name(resultSet.getString("name"))
                .isArchived(resultSet.getBoolean("is_archived"))
                .user(User.builder()
                        .id(resultSet.getLong("user_id"))
                        .role(resultSet.getString("user_role"))
                        .isEnabled(resultSet.getBoolean("user_is_enabled"))
                        .email(resultSet.getString("user_email"))
                        .surname(resultSet.getString("user_surname"))
                        .name(resultSet.getString("user_name"))
                        .build())
                .build();
    }
}
