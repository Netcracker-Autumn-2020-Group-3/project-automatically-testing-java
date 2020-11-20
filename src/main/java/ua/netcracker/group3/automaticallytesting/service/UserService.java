package ua.netcracker.group3.automaticallytesting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

public interface UserService {

    String getUserEmail(User user);

    User buildUser(User user);

    void saveUser(User user);

    void addNewUser(User user);

    User getUserByEmail(String email);

    List<User> getUsers(Pageable pageable, String name, String surname, String email, String role);

    void updateUserById(String email, String name, String surname, String role, boolean is_enabled, long id);
}
