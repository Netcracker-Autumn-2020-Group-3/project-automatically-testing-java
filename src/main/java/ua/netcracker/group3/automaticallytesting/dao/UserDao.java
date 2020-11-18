package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.entity.User;

public interface UserDao {
    User findUserByEmail(String email);
}
