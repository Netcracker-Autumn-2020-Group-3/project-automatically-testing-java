package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.CompoundDAO;
import ua.netcracker.group3.automaticallytesting.model.Compound;
import ua.netcracker.group3.automaticallytesting.service.CompoundService;
import ua.netcracker.group3.automaticallytesting.util.Pageable;
import java.util.List;

@Service
public class CompoundServiceImpl implements CompoundService {

    private CompoundDAO compoundDAO;

    public CompoundServiceImpl(CompoundDAO compoundDAO) {
        this.compoundDAO = compoundDAO;
    }

    @Override
    public List<Compound> getAllCompounds(Pageable pageable) {
        return compoundDAO.findAll(pageable);
    }
}
