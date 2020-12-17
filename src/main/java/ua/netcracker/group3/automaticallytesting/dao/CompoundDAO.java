package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.ActionDtoWithIdNameVoid;
import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDto;
import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.model.CompoundActionWithActionIdAndPriority;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;
import java.util.Optional;

public interface CompoundDAO {

    long getQuantityCompounds(String search);

    List<Compound> findAll(Pageable pageable);

    List<CompoundDtoWithIdName> findAllWithIdName();

    List<CompoundActionWithActionIdAndPriority> findAllCompoundActionsWithActionIdAndPriorityByCompoundId(long compoundId);

    List<ActionDtoWithIdNameVoid> findAllCompoundActionsByCompoundId(long compoundId);

    void archiveCompoundById(long id);

    boolean checkIfNameExist(String name);

    Integer createCompound(Compound compound);

    void createCompoundActions(Integer compoundId,CompoundDto compoundDto);

    /*Compound getCompoundById(Long id);*/

/*    List<Action> getCompoundActions(Integer id);*/

    Optional<Compound> findCompoundById(long id);
    void updateCompound(Compound compound);

    Optional<CompoundDto> findCompActionListById(long id);
}
