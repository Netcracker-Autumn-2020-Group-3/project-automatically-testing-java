package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

@Service
public class CompoundServiceImpl implements CompoundService {

    private final CompoundDAO compoundDAO;

    public CompoundServiceImpl(CompoundDAO compoundDAO) {
        this.compoundDAO = compoundDAO;
    }

    @Override
    public List<Compound> getAllCompounds(Pageable pageable) {
        return compoundDAO.findAll(pageable);
    }

    @Override
    public List<CompoundDtoWithIdName> getAllCompoundsWithIdName() {
        return compoundDAO.findAllWithIdName();
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
    @Override
    public Compound getCompoundById(long id) throws Exception {
        return buildCompoundByID(id);
    }

    @Override
    @Transactional
    public void updateCompound(Compound compound, long id) {
        compound.setId(id);
        compoundDAO.updateCompound(compound);
    }


    private Compound buildCompoundByID(long id) throws Exception {
        Compound compound = compoundDAO.findCompoundById(id).orElseThrow(Exception::new);
        Compound compoundActionList = compoundDAO.findCompActionListById(id).orElseThrow(Exception::new);

        return Compound.builder()
                .id(compound.getId())
                .name(compound.getName())
                .description(compound.getDescription())
                .actionList(compoundActionList.getActionList())
                .build();

    }

}
