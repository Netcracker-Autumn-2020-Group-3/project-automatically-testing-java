package ua.netcracker.group3.automaticallytesting.service;

public interface DashboardService {
    Integer getCountOfUsers();
    Integer getCountOfUsersByRole(String role);
}
