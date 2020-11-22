package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.Compound;

public interface CompoundService {
    Compound getCompoundById(long id) throws Exception;
    void updateCompound(Compound compound, long id);

}
