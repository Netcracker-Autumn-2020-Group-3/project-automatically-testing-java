package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.service.DashboardService;

@Service
public class DashboardServiceImpl implements DashboardService{
    UserDAO userDAO;

    public DashboardServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }
    @Override
    public Integer getCountOfUsers() {
        return userDAO.countUsers();
    }

    @Override
    public Integer getCountOfUsers(String role) {
        return userDAO.countUsers(role);
    }
}
