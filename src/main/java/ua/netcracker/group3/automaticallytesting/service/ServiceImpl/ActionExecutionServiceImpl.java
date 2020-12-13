package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ActionExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecutionPassedFailed;
import ua.netcracker.group3.automaticallytesting.service.ActionExecutionService;

import java.util.List;

@Service
public class ActionExecutionServiceImpl implements ActionExecutionService {

    private final ActionExecutionDAO actionExecutionDAO;

    @Autowired
    public ActionExecutionServiceImpl(ActionExecutionDAO actionExecutionDAO){
        this.actionExecutionDAO = actionExecutionDAO;
    }

    @Override
    public List<ActionExecutionDto> getAllActionExecutions(Long testCaseExecutionId) {
        return actionExecutionDAO.getAllActionExecution(testCaseExecutionId);
    }

    @Override
    public List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(String status) {
        return actionExecutionDAO.getActionExecutionPassedFailed(status);
    }
}
