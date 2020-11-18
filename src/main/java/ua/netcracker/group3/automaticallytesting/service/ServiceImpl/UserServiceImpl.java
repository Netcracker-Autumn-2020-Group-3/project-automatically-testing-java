package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.model.User;
import ua.netcracker.group3.automaticallytesting.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;

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
    public User buildUser(User user) {
            return User.builder()
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .name(user.getName())
                    .surname(user.getSurname())
                    .isEnabled(true)
                    .role("ROLE_USER") //refactor to enum
                    .build();

    }
}
