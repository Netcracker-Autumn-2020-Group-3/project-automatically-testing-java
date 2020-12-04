package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

@Service
public class CompoundServiceImpl implements CompoundService {

    private CompoundDAO compoundDAO;

    public CompoundServiceImpl(CompoundDAO compoundDAO) {
        this.compoundDAO = compoundDAO;
    }

    @Override
    public List<Compound> getAllCompounds(Pageable pageable) {
        return compoundDAO.findAll(pageable);
    }

    @Override
    public boolean checkIfNameExist(String name) {
        return compoundDAO.checkIfNameExist(name);
    }

    @Override
    public Integer createCompound(Compound compound) {
        return compoundDAO.createCompound(compound);
    }

    @Override
    public void createCompoundActions(List<CompoundAction> compoundActions) {
        compoundDAO.createCompoundActions(compoundActions);
    }
}
