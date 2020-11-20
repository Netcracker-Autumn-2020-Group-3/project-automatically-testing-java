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
    @Value("${get.users.page.asc}")
    private String GET_USERS_PAGE_ASC;
    @Value("${get.users.page.desc}")
    private String GET_USERS_PAGE_DESC;

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

    }

    @Override
    public List<Map<String, Object>> getAll() {
        return jdbcTemplate.queryForList(SqlConstant.GET_ALL_USER);
    }

    @Override
    public Optional<User> findUserById(long id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_ID, mapperWithoutPassword, id));
    }


    @Override
    public List<User> getUsersAsc(String sortField, int pageSize, int offset, String name, String surname,String email, String role) {
        return jdbcTemplate.queryForStream(GET_USERS_PAGE_ASC, mapperWithoutPassword, name, surname, email, role,
                sortField, pageSize, offset)
                .collect(Collectors.toList());
    }
    @Override
    public List<User> getUsersDesc(String sortField, int pageSize, int offset, String name, String surname,String email, String role) {
        return jdbcTemplate.queryForStream(GET_USERS_PAGE_DESC, mapperWithoutPassword, name, surname, email, role,
                sortField, pageSize, offset)
                .collect(Collectors.toList());
    }
}
