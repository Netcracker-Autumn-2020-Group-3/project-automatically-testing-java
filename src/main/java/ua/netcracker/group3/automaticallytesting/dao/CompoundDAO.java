package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.model.CompoundAction;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

public interface CompoundDAO {

    List<Compound> findAll(Pageable pageable);

    boolean checkIfNameExist(String name);

    Integer createCompound(Compound compound);

    void createCompoundActions(List<CompoundAction> compoundActions);
}
