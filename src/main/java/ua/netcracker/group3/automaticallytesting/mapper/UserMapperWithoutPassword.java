package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ua.netcracker.group3.automaticallytesting.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserMapperWithoutPassword  implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        return User.builder().name(resultSet.getString("name"))
                .surname(resultSet.getString("surname"))
                .email(resultSet.getString("email"))
                .role(resultSet.getString("role"))
                .isEnabled(resultSet.getBoolean("is_enabled"))
                .userId(resultSet.getLong("id"))
                .build();
    }
}
