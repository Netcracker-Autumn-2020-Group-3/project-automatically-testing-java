package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

/**
 * @author Danya Polishchuk
 */

public interface CompoundDAO {

    List<Compound> findAll(Pageable pageable);

}
