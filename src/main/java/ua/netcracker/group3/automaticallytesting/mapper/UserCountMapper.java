package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.UserCountDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserCountMapper implements RowMapper<UserCountDto> {
    @Override
    public UserCountDto mapRow(ResultSet rs, int i) throws SQLException {
        return UserCountDto.builder()
                .userCount(rs.getLong("user_count"))
                .adminCount(rs.getLong("admin_count"))
                .managerCount(rs.getLong("manager_count"))
                .engineerCount(rs.getLong("engineer_count"))
                .build();

    }
}
