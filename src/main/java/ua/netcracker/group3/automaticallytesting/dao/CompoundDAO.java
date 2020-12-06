package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.CompoundDtoWithIdName;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.model.CompoundActionWithActionIdAndPriority;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;
import java.util.Optional;

public interface CompoundDAO {

    List<Compound> findAll(Pageable pageable);

    List<CompoundDtoWithIdName> findAllWithIdName();

    List<CompoundActionWithActionIdAndPriority> findAllCompoundActionsWithActionIdAndPriorityByCompoundId(long compoundId);

    boolean checkIfNameExist(String name);

    Integer createCompound(Compound compound);

    void createCompoundActions(List<CompoundAction> compoundActions);

    Optional<Compound> findCompoundById(long id);
    void updateCompound(Compound compound);
}
