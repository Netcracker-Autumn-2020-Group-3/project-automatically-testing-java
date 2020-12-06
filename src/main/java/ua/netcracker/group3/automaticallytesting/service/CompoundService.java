package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.CompoundDto;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

public interface CompoundService {

    List<Compound> getAllCompounds(Pageable pageable);

    List<CompoundDtoWithIdName> getAllCompoundsWithIdName();

    boolean checkIfNameExist(String name);

    Integer createCompound(Compound compound);

    void createCompoundActions(List<CompoundAction> compoundActions);

    CompoundDto getCompoundById(long id) throws Exception;

    void updateCompound(Compound compound, long id);


}
