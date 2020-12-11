package ua.netcracker.group3.automaticallytesting.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.netcracker.group3.automaticallytesting.model.Role;
import ua.netcracker.group3.automaticallytesting.service.DashboardService;

@CrossOrigin("*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }


    @GetMapping("/user-count")
    public ResponseEntity<?> getUsersCount() {
        return new ResponseEntity<>(dashboardService.getCountOfUsers(), HttpStatus.OK);
    }

    @GetMapping("/admin-count")
    public ResponseEntity<?> getUsersCountByRole() {
        return new ResponseEntity<>(dashboardService.getCountOfUsersByRole(Role.ADMIN.toString()), HttpStatus.OK);
    }

    @GetMapping("/manager-count")
    public ResponseEntity<?> getUsersCountByRole() {
        return new ResponseEntity<>(dashboardService.getCountOfUsersByRole(Role.MANAGER.toString()), HttpStatus.OK);
    }

    @GetMapping("/engineer-count")
    public ResponseEntity<?> getUsersCountByRole() {
        return new ResponseEntity<>(dashboardService.getCountOfUsersByRole(Role.ENGINEER.toString()), HttpStatus.OK);
    }

}
