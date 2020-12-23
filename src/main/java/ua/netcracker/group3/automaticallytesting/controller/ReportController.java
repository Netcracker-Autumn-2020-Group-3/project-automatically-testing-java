package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.SubscribedUserTestCaseDto;
import ua.netcracker.group3.automaticallytesting.service.ReportService;
import java.util.List;

@RestController
@CrossOrigin(origins = "https://automatically-testing-angular.herokuapp.com")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService){
        this.reportService = reportService;
    }

    /**
     * Returns ResponseEntity with status OK if report was send successfully
     * Method checks if subscribed users are present, if not method won`t send report
     * @param testCaseExecutionId needed for getting list of subscribed users
     * @param actionExecutionList needed for sending report with action executions
     * @return ResponseEntity with status OK if report was send successfully
     */
    @PostMapping("/send/report/{testCaseExecutionId}")
    public ResponseEntity<?> sendReport(@PathVariable Long testCaseExecutionId,
                                     @RequestBody List<ActionExecutionDto> actionExecutionList){
        List<SubscribedUserTestCaseDto> subscribedUsers = reportService.getSubscribedUsers(testCaseExecutionId);
        if (subscribedUsers.size() >= 1) {
            return reportService.sendReportToUser(actionExecutionList, subscribedUsers);
        }else{
            return ResponseEntity.ok(HttpStatus.NO_CONTENT);
        }
    }
}
