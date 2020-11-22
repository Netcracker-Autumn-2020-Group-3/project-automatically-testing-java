package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.controller.Constant.SqlConstant;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.exception.UserNotFoundException;
import ua.netcracker.group3.automaticallytesting.mapper.UserMapper;
import ua.netcracker.group3.automaticallytesting.mapper.UserMapperWithoutPassword;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@PropertySource("classpath:queries/postgres.properties")
@Repository
public class UserDAOImpl implements UserDAO {

    private JdbcTemplate jdbcTemplate;
    private UserMapper mapper;
    private UserMapperWithoutPassword mapperWithoutPassword;

    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate, UserMapper mapper, UserMapperWithoutPassword mapperWithoutPassword) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.mapperWithoutPassword = mapperWithoutPassword;
    }

    @Value("${find.user.by.email}")
    private String FIND_USER_BY_EMAIL;
    @Value("${find.user.by.email.with.password}")
    private String FIND_USER_BY_EMAIL_WITH_PASSWORD;
    @Value("${find.user.by.id}")
    private String FIND_USER_BY_ID;
    @Value("${update.user.by.id}")
    private String UPDATE_USER_BY_ID;
    @Value("${count.users}")
    private String COUNT_USERS;
    @Value("${get.users}")
    private String GET_USERS;
    @Value("${insert.user}")
    private String INSERT_USER;

    @Override
    public User findUserByEmail(String email) {
        return jdbcTemplate.queryForObject(FIND_USER_BY_EMAIL_WITH_PASSWORD, mapper, email);
    }

    @Override
    public String getEmail(Long userId) {
        return jdbcTemplate.queryForObject(SqlConstant.GET_EMAIL_BY_ID + userId, String.class);
    }

    @Override
    public void saveUser(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String name = user.getName();
        String surname = user.getSurname();
        String role = user.getRole();
        boolean enabled = user.isEnabled();
        jdbcTemplate.update(INSERT_USER, email, password, name, surname, role, enabled);

    }

    @Override
    public List<Map<String, Object>> getAll() {
        return jdbcTemplate.queryForList(SqlConstant.GET_ALL_USER);
    }

    @Override
    public Optional<User> findUserById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_ID, mapperWithoutPassword, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


    @Override
    public List<User> getUsersPageSorted(String orderByLimitOffsetWithValues, String name, String surname, String email, String role) {
        return jdbcTemplate.queryForStream(GET_USERS + orderByLimitOffsetWithValues,
                mapperWithoutPassword, name, surname, email, role)
                .collect(Collectors.toList());
    }

    @Override
    public void updateUserById(String email, String name, String surname, String role, boolean is_enabled, long id) {
        jdbcTemplate.update(UPDATE_USER_BY_ID, email, name, surname, role, is_enabled, id);
    }

    @Override
    public Integer countUsers() {
        return jdbcTemplate.queryForObject(COUNT_USERS, Integer.class);
    }
}
