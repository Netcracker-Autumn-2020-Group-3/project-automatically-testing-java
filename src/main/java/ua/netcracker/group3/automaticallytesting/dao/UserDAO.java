package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDAO {

    public List<Map<String, Object>> getAll();

    public String getEmail(Long userId);

    public void saveUser(User user);


}
