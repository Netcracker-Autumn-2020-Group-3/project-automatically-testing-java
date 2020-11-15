package ua.netcracker.group3.automaticallytesting.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserDAO {

    public List<Map<String, Object>> getAll();

    public String getEmail(Long userId);


}
