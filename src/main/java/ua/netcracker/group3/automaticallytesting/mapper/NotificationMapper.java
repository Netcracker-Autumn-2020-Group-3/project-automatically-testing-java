package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.dto.NotificationDto;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class NotificationMapper implements RowMapper<NotificationDto> {
    @Override
    public NotificationDto mapRow(ResultSet resultSet, int i) throws SQLException {
        return NotificationDto.builder()
                .status(resultSet.getString(1))
                .startTime(resultSet.getString(2))
                .name(resultSet.getString(3))
                .id(resultSet.getLong(4))
                .build();
    }
}
