package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.controller.Constant.SqlConstant;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.mapper.UserMapper;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@PropertySource("classpath:queries/postgres.properties")
@Repository
public class UserDAOImpl implements UserDAO {

    JdbcTemplate jdbcTemplate;
    UserMapper mapper;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate, UserMapper mapper){
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
    }

    @Value("find.user.by.email")
    private String FIND_USER_BY_EMAIL;
    @Value("find.user.by.id")
    private String FIND_USER_BY_ID;
    @Value("get.users.page")
    private String GET_USERS_PAGE;

    @Override
    public User findUserByEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_USER_BY_EMAIL, mapper, "dd");
        /*return jdbcTemplate
                .query(SqlConstant.GET_USER_BY_EMAIL, (PreparedStatementSetter) preparedStatement ->
                                preparedStatement.setString(1, email), mapper)
                .get(0);*/
    }

    @Override
    public String getEmail(Long userId) {
        return jdbcTemplate.queryForObject(SqlConstant.GET_EMAIL_BY_ID + userId, String.class);
    }

    @Override
    public void saveUser(User user) {

    }


    @Override
    public List<Map<String, Object>> getAll() {
        return jdbcTemplate.queryForList(SqlConstant.GET_ALL_USER);
    }

    @Override
    public Optional<User> findUserById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_ID, mapper, id));
    }

    @Override
    public List<User> getUsersPage(Pageable pageable) {
        System.out.println(GET_USERS_PAGE);
        return jdbcTemplate.queryForStream(GET_USERS_PAGE, mapper,
                pageable.getSortField(), pageable.getSortOrder().name(), pageable.getPageSize(), pageable.getOffset())
                .collect(Collectors.toList());
    }
}
