package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.Action;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

public interface CompoundDAO {

    List<Compound> findAll(Pageable pageable);

    boolean checkIfNameExist(String name);

    Integer createCompound(Compound compound);

    void createCompoundActions(List<CompoundAction> compoundActions);

    Compound getCompoundById(Long id);

    List<Action> getCompoundActions(Integer id);
}
