package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.exception.UserNotFoundException;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.UserService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.util.Pagination;

import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    UserDAO userDAO;
    Pagination pagination;

    @Autowired
    public UserServiceImpl(Pagination pagination, UserDAO userDAO) {
        this.pagination = pagination;
        this.userDAO = userDAO;
    }


    @Override
    public String getUserEmail(User user) {
        return userDAO.getEmail(user.getUserId());
    }

    @Override
    @Transactional
    public void saveUser(User userRequest) {
        User user = buildUser(userRequest);
        userDAO.saveUser(user);
    }


    @Override
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    private String replaceNullsForSearch(String val) {
        return val == null ? "%" : val;
    }

    @Override
    public List<User> getUsers(Pageable pageable, String name, String surname, String email, String role) {
        pageable = pagination.replaceNullsUserPage(pageable);

        if (pageable.getSortOrder().equalsIgnoreCase("ASC")) {
            return userDAO.getUsersAsc(pageable.getSortField(), pageable.getPageSize(), pageable.getOffset(),
                    replaceNullsForSearch(name), replaceNullsForSearch(surname), replaceNullsForSearch(email), replaceNullsForSearch(role));
        } else {
            return userDAO.getUsersDesc(pageable.getSortField(), pageable.getPageSize(), pageable.getOffset(),
                    replaceNullsForSearch(name), replaceNullsForSearch(surname), replaceNullsForSearch(email), replaceNullsForSearch(role));
        }
    }

    public User getUserById(long id) throws UserNotFoundException {
        return userDAO.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void updateUserById(String email, String name, String surname, String role, boolean is_enabled, long id) {
        userDAO.updateUserById(email, name, surname, role, is_enabled, id);
    }

    private User buildUser(User user) {
        return User.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .surname(user.getSurname())
                .isEnabled(true)
                .role(user.getRole())
                .build();

    }
}
