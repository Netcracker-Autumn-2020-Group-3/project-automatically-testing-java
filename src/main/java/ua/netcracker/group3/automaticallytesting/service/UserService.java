package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

public interface UserService {

    String getUserEmail(User user);


    void saveUser(User user);


    User getUserByEmail(String email);

    List<User> getUsers(Pageable pageable, String name, String surname, String email, String role);

    void updateUserById(String email, String name, String surname, String role, boolean is_enabled, long id);

    Integer countPages(Integer pageSize);

    User getUserById(long id);

    void updateUserPassword(String token, String password) throws Exception;
}
