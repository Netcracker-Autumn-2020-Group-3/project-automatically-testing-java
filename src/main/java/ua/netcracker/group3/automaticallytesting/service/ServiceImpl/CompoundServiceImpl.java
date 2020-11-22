package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
@Service
public class CompoundServiceImpl implements CompoundService {
    private CompoundDAO compoundDAO;

    @Autowired
    public CompoundServiceImpl(CompoundDAO compoundDAO) {
        this.compoundDAO = compoundDAO;
    }

    @Override
    public Compound getCompoundById(long id) throws Exception {
        return buildCompoundByID(id);
    }

    @Override
    @Transactional
    public void updateCompound(Compound compound, long id) {
        compound.setCompound_id(id);
        compoundDAO.updateCompound(compound);
    }


    private Compound buildCompoundByID(long id) throws Exception {
        Compound compound = compoundDAO.findCompoundById(id).orElseThrow(Exception::new);
        Compound compoundActionList = compoundDAO.findCompActionListById(id).orElseThrow(Exception::new);

        return Compound.builder()
                .compound_id(compound.getCompound_id())
                .name(compound.getName())
                .description(compound.getDescription())
                .actionList(compoundActionList.getActionList())
                .build();

    }
}
