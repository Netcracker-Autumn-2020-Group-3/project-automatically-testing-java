package ua.netcracker.group3.automaticallytesting.dao;


import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDAO {
    User findUserByEmail(String email);
    public String getEmail(Long userId);
    public void saveUser(User user);

    public List<Map<String, Object>> getAll();


    Optional<User> findUserById(long id);

    List<User> getUsersPageSorted(String orderByLimitOffsetWithValues,  String name, String surname, String email, String role);

    void updateUserById(String email, String name,String surname,String role,boolean is_enabled, long id);

    Integer countUsers();
}
