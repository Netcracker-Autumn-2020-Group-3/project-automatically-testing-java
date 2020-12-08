package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ActionDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.service.ActionService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import ua.netcracker.group3.automaticallytesting.util.Pagination;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {

    private ActionDAO actionDAO;
    Pagination pagination;

    @Autowired
    public ActionServiceImpl(Pagination pagination,ActionDAO actionDAO) {
        this.actionDAO = actionDAO;
        this.pagination = pagination;
    }


    @Override
    public List<Action> getAllActions(Pageable pageable) {
        pageable = pagination.setDefaultOrderValue(pageable);
        return actionDAO.getPageActions(pagination.formSqlPostgresPaginationAction(pageable));
    }

    @Override
    public List<Action> getAllActions() {
        return actionDAO.getAllActions();
    }

    @Override
    public List<Action> findActionsByName(String name,Pageable pageable) {
        pageable = pagination.setDefaultOrderValue(pageable);
        return actionDAO.findActionsByName(pagination.formSqlPostgresPaginationAction(pageable),name);
    }

    @Override
    public List<ActionDtoWithIdName> getAllActionsWithIdName() {
        return actionDAO.findAllWithIdName();
    }

    @Override
    public Integer getNumberOfActions() {
        return actionDAO.getNumberOfActions();
    }

    @Override
    public long createAction(String name, String description) {
        return actionDAO.createAction(name, description);
    }
}
