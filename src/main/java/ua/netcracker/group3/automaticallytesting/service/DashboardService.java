package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.UserCountDto;

import java.util.List;

public interface DashboardService {

    UserCountDto getCountOfUsersByRole();
}
