package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdNameVoid;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDto;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

public interface CompoundService {

    long getQuantityCompounds(String search);

    List<Compound> getAllCompounds(Pageable pageable);

    List<CompoundDtoWithIdName> getAllCompoundsWithIdName();

    List<ActionDtoWithIdNameVoid> getAllCompoundActionsByCompoundId(long compoundId);

    void archiveCompoundById(long id);

    boolean checkIfNameExist(String name);

    void createCompound(CompoundDto compoundDto);

    //void createCompoundActions(List<CompoundAction> compoundActions);

    CompoundDto getCompoundById(long id) throws Exception;

    void updateCompound(Compound compound, long id);


}
