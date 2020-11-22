package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.Compound;

import java.util.Optional;

public interface CompoundDAO {
    Optional<Compound> findCompoundById(long id);
    Optional<Compound> findCompActionListById(long id);
    void updateCompound(Compound compound);
//    void insertActionById(long id);
}
