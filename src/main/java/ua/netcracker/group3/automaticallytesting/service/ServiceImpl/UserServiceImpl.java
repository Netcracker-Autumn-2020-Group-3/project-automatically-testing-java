package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void saveUser(User user) {

    }

    @Override
    public void addNewUser(User user) {
    }

    @Override
    public User getUserByEmail(String email) {
        return userDAO.findUserByEmail(email);
    }

    @Override
    public List<User> getUsers(Pageable pageable, String name, String surname, String email, String role) {
        pageable = pagination.replaceNullsUserPage(pageable);
        name = name == null ? "%" : name;
        surname = surname == null ? "%" : surname;
        email = email == null ? "%" : email;
        role = role == null ? "%" : role;
        if (pageable.getSortOrder().equals("ASC")) {
            return userDAO.getUsersAsc(pageable.getSortField(), pageable.getPageSize(), pageable.getOffset(), name, surname, email, role);
        } else {
            return userDAO.getUsersDesc(pageable.getSortField(), pageable.getPageSize(), pageable.getOffset(), name, surname, email, role);
        }
    }

    public User getUserById(long id) throws UserNotFoundException {
        return userDAO.findUserById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Override
    public void updateUserById(String email, String name, String surname, String role, boolean is_enabled, long id) {
         userDAO.updateUserById(email, name, surname, role, is_enabled, id);
    }

    @Override
    public User buildUser(User user) {
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
