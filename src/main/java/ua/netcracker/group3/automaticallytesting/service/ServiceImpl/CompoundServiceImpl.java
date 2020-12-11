package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDto;
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
    public long getQuantityCompounds() {
        return compoundDAO.getQuantityCompounds();
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
    public void createCompound(CompoundDto compoundDto) {
        Compound compound = Compound.builder().name(compoundDto.getName()).description(compoundDto.getDescription()).build();
        Integer compoundId = compoundDAO.createCompound(compound);
        compoundDAO.createCompoundActions(compoundId,compoundDto);
    }

    @Override
    public CompoundDto getCompoundById(long id) throws Exception {
        return buildCompoundByID(id);
    }

    @Override
    @Transactional
    public void updateCompound(Compound compound, long id) {
        compound.setId(id);
        compoundDAO.updateCompound(compound);
    }


    private CompoundDto buildCompoundByID(long id) throws Exception {
        Compound compound = compoundDAO.findCompoundById(id).orElseThrow(Exception::new);
        //CompoundDto compoundActionList = compoundDAO.findCompActionListById(id).orElseThrow(Exception::new);
        return CompoundDto.builder()
                .id(compound.getId())
                .name(compound.getName())
                .description(compound.getDescription())
                //.actionList(compoundActionList.getActionList())
                .build();

    }

}
