package ua.netcracker.group3.automaticallytesting.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.UserDao;
import ua.netcracker.group3.automaticallytesting.entity.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRepositoryImpl implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public User findUserByEmail(String email) {
        String userQuery = "select * from \"user\" where email = ?";


         List<User> arrayList =  jdbcTemplate.query(userQuery,
                preparedStatement -> preparedStatement.setString(1, email),
                new RowMapper<User>() {
                    @Override
                    public User mapRow(ResultSet rs, int i) throws SQLException {
                        return new User(rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getBoolean(7)
                        );
                    }
                });

          return arrayList.get(0);
    }

}
