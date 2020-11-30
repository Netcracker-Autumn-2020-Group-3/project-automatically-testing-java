package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

public interface CompoundService {

    List<Compound> getAllCompounds(Pageable pageable);
}