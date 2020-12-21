package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdNameVoid;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDto;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

@Service
@Slf4j
public class CompoundServiceImpl implements CompoundService {

    private final CompoundDAO compoundDAO;

    public CompoundServiceImpl(CompoundDAO compoundDAO) {
        this.compoundDAO = compoundDAO;
    }

    @Override
    public List<Compound> getAllCompounds(Pageable pageable) {
        List<Compound> compounds = compoundDAO.findAll(pageable);
        if(compounds.isEmpty()) {
            log.warn("IN getAllCompounds - no compounds found with pagination: {}", pageable);
            return compounds;
        }
        log.info("IN getAllCompounds - compounds: {} found with pagination: {}", compounds, pageable);
        return compounds;
    }

    @Override
    public long getQuantityCompounds(String search) {
        long quantity = compoundDAO.getQuantityCompounds(search);
        log.info("IN getQuantityCompounds - {} compounds found with name like: '{}'", quantity, search);
        return quantity;
    }

    @Override
    public List<CompoundDtoWithIdName> getAllCompoundsWithIdName() {
        List<CompoundDtoWithIdName> compounds = compoundDAO.findAllWithIdName();
        if(compounds.isEmpty()) {
            log.warn("IN getAllCompoundsWithIdName - no compounds found");
            return compounds;
        }
        log.info("IN getAllCompoundsWithIdName - {} compounds found", compounds.size());
        return compounds;
    }

    @Override
    public List<ActionDtoWithIdNameVoid> getAllCompoundActionsByCompoundId(long id) {
        List<ActionDtoWithIdNameVoid> actions = compoundDAO.findAllCompoundActionsByCompoundId(id);
        if(actions.isEmpty()) {
            log.warn("IN getAllCompoundActionsByCompoundId - no actions found with compound id: {}", id);
            return actions;
        }
        log.info("IN getAllCompoundActionsByCompoundId - actions: {} found with compound id: {}", actions, id);
        return actions;
    }

    @Override
    public void archiveCompoundById(long id) {
        compoundDAO.archiveCompoundById(id);
        log.info("IN archiveCompoundById - compound with id: {} is archived", id);
    }

    @Override
    public boolean checkIfNameExist(String name) {
        boolean isExist = compoundDAO.checkIfNameExist(name);
        String wordExist = isExist ? "already exists" : "does not exist";
        log.info("IN checkIfNameExist - compound with name: {} {}", name, wordExist);
        return isExist;
    }

    /**
     * Void method that creates compound in DB
     * @param compoundDto contains values and info for creating compound in DB
     */
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
    public void updateCompound(Compound compound, long id) {
        compound.setId(id);
        compoundDAO.updateCompound(compound);
    }


    private CompoundDto buildCompoundByID(long id) throws Exception {
        Compound compound = compoundDAO.findCompoundById(id).orElseThrow(Exception::new);
        return CompoundDto.builder()
                .id(compound.getId())
                .name(compound.getName())
                .description(compound.getDescription())
                .build();

    }

}
