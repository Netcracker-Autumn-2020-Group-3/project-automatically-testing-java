package ua.netcracker.group3.automaticallytesting.mapper;

import org.springframework.jdbc.core.RowMapper;
import ua.netcracker.group3.automaticallytesting.model.User;

import javax.swing.tree.TreePath;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {
    public static final String BASE_SQL
            = "SELECT user_id, email, password, " +
            "name, surname, role, is_enabled FROM \"user\"";
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {


        Long userId = rs.getLong("user_id");
        String email = rs.getString("email");
        String password = rs.getString("password");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String role = rs.getString("role");
        boolean isEnabled = rs.getBoolean("is_enabled");

        return new User(userId,email,password,name,surname,role,isEnabled);
    }

}
