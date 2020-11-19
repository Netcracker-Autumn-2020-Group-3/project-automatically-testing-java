package ua.netcracker.group3.automaticallytesting.dao;


import ua.netcracker.group3.automaticallytesting.model.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    User findUserByEmail(String email);
    public String getEmail(Long userId);
    public void saveUser(User user);

    public List<Map<String, Object>> getAll();
}
