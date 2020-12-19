package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.SubscribedUserTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.service.ReportService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    @PostMapping("/send/report/{testCaseExecutionId}")
    public ResponseEntity<?> sendReport(@RequestHeader("Authorization") String jwt,
                                     @PathVariable Long testCaseExecutionId,
                                     @RequestBody List<ActionExecutionDto> actionExecutionList){
        List<SubscribedUserTestCaseDto> subscribedUsers = reportService.getSubscribedUsers(testCaseExecutionId);
        if (subscribedUsers.size() >= 1) {
            return reportService.sendReportToUser(actionExecutionList, subscribedUsers);
        }else{
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
    }
}
