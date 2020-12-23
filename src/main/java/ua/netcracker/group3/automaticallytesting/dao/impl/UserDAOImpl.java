package ua.netcracker.group3.automaticallytesting.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.dto.UserCountDto;
import ua.netcracker.group3.automaticallytesting.mapper.UserCountMapper;
import ua.netcracker.group3.automaticallytesting.mapper.UserMapper;
import ua.netcracker.group3.automaticallytesting.mapper.UserMapperWithoutPassword;
import ua.netcracker.group3.automaticallytesting.model.User;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PropertySource("classpath:queries/postgres.properties")
@Repository
public class UserDAOImpl implements UserDAO {

    private final JdbcTemplate jdbcTemplate;
    private final UserMapper mapper;
    private final UserMapperWithoutPassword mapperWithoutPassword;
    private final UserCountMapper userCountMapper;
    @Autowired
    public UserDAOImpl(JdbcTemplate jdbcTemplate, UserMapper mapper, UserMapperWithoutPassword mapperWithoutPassword, UserCountMapper userCountMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.mapper = mapper;
        this.mapperWithoutPassword = mapperWithoutPassword;
        this.userCountMapper = userCountMapper;
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
    @Value("${count.users.search}")
    private String COUNT_USERS_SEARCH;
    @Value("${insert.user}")
    private String INSERT_USER;
    @Value("${get.user.email.by.id}")
    private String GET_USER_EMAIL_BY_ID;
    @Value("${update.user.password}")
    private String UPDATE_USER_PASS;
    @Value("${update.user.settings}")
    private String UPDATE_SETTINGS;
    @Value("${get.user.id.by.email}")
    private String GET_USER_ID_BY_EMAIL;

    @Value("${get.user.count}")
    private String COUNT_USERS_BY_ROLE;
    @Value("${count.users.by.role}")
    private String COUNT_BY_ROLE;
    @Value("${check.if.user.email.exists}")
    private String CHECK_IF_EMAIL_EXISTS;


    @Override
    public User findUserByEmail(String email) {

        return jdbcTemplate.queryForObject(FIND_USER_BY_EMAIL_WITH_PASSWORD, mapper, email);
    }

    @Override
    public String getEmail(Long id) {
        return jdbcTemplate.queryForObject(GET_USER_EMAIL_BY_ID, String.class, id);
    }

    @Override
    public Long getUserIdByEmail(String email) {
        return jdbcTemplate.queryForObject(GET_USER_ID_BY_EMAIL, Long.class, email);
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
    public Optional<User> findUserById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_USER_BY_ID, mapperWithoutPassword, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<User> getUsersPageSorted(String orderByLimitOffsetWithValues, String isEnabledFiltering, String name, String surname, String email, String roles) {
        return jdbcTemplate.queryForStream(GET_USERS + isEnabledFiltering + orderByLimitOffsetWithValues,
                mapperWithoutPassword, name, surname, email, roles)
                .collect(Collectors.toList());
    }

    @Override
    public Integer countUsersSearch(String enabledSql, String name, String surname, String email, String roles) {
        return jdbcTemplate.queryForObject(COUNT_USERS_SEARCH + enabledSql, Integer.class, name, surname, email, roles);
    }

    @Override
    public void updateUserById(String email, String name, String surname, String role, boolean is_enabled, long id) {
        jdbcTemplate.update(UPDATE_USER_BY_ID, email, name, surname, role, is_enabled, id);
    }

    @Override
    public Integer countUsers() {
        return jdbcTemplate.queryForObject(COUNT_USERS, Integer.class);
    }

    @Override
    public void updateUserPassword(String email, String password) {
        jdbcTemplate.update(UPDATE_USER_PASS, password, email);
    }

    @Override
    public void updateUserSettings(User user) {
        jdbcTemplate.update(UPDATE_SETTINGS, user.getName(), user.getSurname(), user.getEmail());
    }

    @Override
    public UserCountDto countOfUsersByRole() {
        return jdbcTemplate.queryForObject(COUNT_USERS_BY_ROLE, userCountMapper);
    }

    @Override
    public Boolean checkIfEmailExists(String email) {
        Integer temp = jdbcTemplate.queryForObject(CHECK_IF_EMAIL_EXISTS, Integer.class, email);
        if(temp != null) {
            return temp == 1;
        }
        else {
            return false;
        }
    }
}
