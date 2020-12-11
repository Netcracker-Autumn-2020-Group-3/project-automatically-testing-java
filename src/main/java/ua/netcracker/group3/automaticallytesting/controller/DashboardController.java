package ua.netcracker.group3.automaticallytesting.controller;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.Role;
import ua.netcracker.group3.automaticallytesting.service.DashboardService;

import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    // role-id:
    // 1: ADMIN
    // 2: MANAGER
    // 3: ENGINEER
    @GetMapping("/user-count")
    public ResponseEntity<?> getUsersCount(@RequestParam(value = "role-id", required = false) String roleID) {
        Integer count = 0;
        if (roleID == null) {
            count = dashboardService.getCountOfUsers();
        }
        else if(roleID.equals("1")) {
            count = dashboardService.getCountOfUsers(Role.ADMIN.toString());
        }
        else if(roleID.equals("2")) {
            count = dashboardService.getCountOfUsers(Role.MANAGER.toString());
        }
        else if (roleID.equals("3")) {
            count = dashboardService.getCountOfUsers(Role.ENGINEER.toString());
        }

        return new ResponseEntity<>(count, HttpStatus.OK);
    }


}
