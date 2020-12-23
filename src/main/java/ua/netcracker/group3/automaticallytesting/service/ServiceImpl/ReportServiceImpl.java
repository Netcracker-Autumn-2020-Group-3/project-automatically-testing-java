package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.config.JwtProvider;
import ua.netcracker.group3.automaticallytesting.dao.ReportDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.dto.SubscribedUserTestCaseDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecution;
import ua.netcracker.group3.automaticallytesting.service.ReportService;

import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final EmailServiceImpl emailService;
    private final ReportDAO reportDAO;

    @Autowired
    public ReportServiceImpl(EmailServiceImpl emailService,ReportDAO reportDAO){
        this.emailService = emailService;
        this.reportDAO = reportDAO;
    }

    /**
     * @param actionExecutionList contains action executions
     * @param subscribedUsers contains subscribed users
     * @return ResponseEntity with status
     */
    @Override
    public ResponseEntity<?> sendReportToUser(List<ActionExecutionDto> actionExecutionList, List<SubscribedUserTestCaseDto> subscribedUsers) {
        return emailService.sendReportToUser(actionExecutionList,subscribedUsers);
    }

    /**
     * @param testCaseExecutionId needed for getting value from DB by id
     * @return list of subscribed users
     */
    @Override
    public List<SubscribedUserTestCaseDto> getSubscribedUsers(Long testCaseExecutionId) {
        return reportDAO.getSubscribedUsers(testCaseExecutionId);
    }
}
