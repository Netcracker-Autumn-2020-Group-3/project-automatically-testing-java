package ua.netcracker.group3.automaticallytesting.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.controller.Constant.SqlConstant;
import ua.netcracker.group3.automaticallytesting.mapper.UserMapper;
import ua.netcracker.group3.automaticallytesting.model.User;

import java.util.List;
import java.util.Map;

@Repository
public class UserDAOImpl implements UserDAO {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    UserMapper mapper;

    @Override
    public User findUserByEmail(String email) {
        return jdbcTemplate
                .query(SqlConstant.GET_USER_BY_EMAIL, (PreparedStatementSetter) preparedStatement ->
                    preparedStatement.setString(1, email)
                ,mapper)
                .get(0);
    }

    @Override
    public String getEmail(Long userId) {
        return jdbcTemplate.queryForObject(SqlConstant.GET_EMAIL_BY_ID+userId, String.class);
    }

    @Override
    public void saveUser(User user) {

    }


    @Override
    public List<Map<String, Object>> getAll() {
        return jdbcTemplate.queryForList(SqlConstant.GET_ALL_USER);
    }
}
