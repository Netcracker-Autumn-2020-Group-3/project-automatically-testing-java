package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.UserDAO;
import ua.netcracker.group3.automaticallytesting.dto.UserCountDto;
import ua.netcracker.group3.automaticallytesting.service.DashboardService;

import java.util.List;

@Service
public class DashboardServiceImpl implements DashboardService{
    UserDAO userDAO;

    public DashboardServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserCountDto getCountOfUsersByRole() {
        return userDAO.countOfUsersByRole();
    }
}
