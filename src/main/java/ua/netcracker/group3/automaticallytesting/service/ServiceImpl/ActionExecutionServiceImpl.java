package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ActionExecutionDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionExecutionDto;
import ua.netcracker.group3.automaticallytesting.model.ActionExecutionPassedFailed;
import ua.netcracker.group3.automaticallytesting.service.ActionExecutionService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.util.Pagination;

import java.util.List;

@Service
public class ActionExecutionServiceImpl implements ActionExecutionService {

    private final ActionExecutionDAO actionExecutionDAO;
    private final Pagination pagination;

    @Autowired
    public ActionExecutionServiceImpl(ActionExecutionDAO actionExecutionDAO,Pagination pagination){
        this.actionExecutionDAO = actionExecutionDAO;
        this.pagination = pagination;
    }

    @Override
    public List<ActionExecutionDto> getAllActionExecutions(Long testCaseExecutionId, Pageable pageable) {
        return actionExecutionDAO.getAllActionExecution(testCaseExecutionId,
                                                        pagination.formSqlPostgresPaginationPiece(pageable),
                                                        pageable.getSearch());
    }

    @Override
    public List<ActionExecutionPassedFailed> getActionExecutionPassedFailed(String status) {
        return actionExecutionDAO.getActionExecutionPassedFailed(status);
    }

    @Override
    public Integer getQuantityActionsExecutions(Long testCaseExecutionId,String searchName) {
        return actionExecutionDAO.getQuantityActionsExecutions(testCaseExecutionId,searchName);
    }
}
