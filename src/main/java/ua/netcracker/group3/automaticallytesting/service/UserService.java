package ua.netcracker.group3.automaticallytesting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.model.User;

public interface UserService {

    public String getUserEmail(User user);

}
