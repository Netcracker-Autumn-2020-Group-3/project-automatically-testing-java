package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.ActionDAO;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.service.ActionService;

import java.util.List;

@Service
public class ActionServiceImpl implements ActionService {

    private ActionDAO actionDAO;


    @Autowired
    public ActionServiceImpl(ActionDAO actionDAO) {
        this.actionDAO = actionDAO;
    }


    @Override
    public List<Action> getAllActions() {
        return actionDAO.getPageActions();
    }

    @Override
    public List<Action> findActionsByName(String name) {
        return actionDAO.findActionsByName(name);
    }
}
