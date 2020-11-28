package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.DataEntryDAO;
import ua.netcracker.group3.automaticallytesting.service.DataEntryService;

@Service
public class DataEntryServiceImpl implements DataEntryService {

    DataEntryDAO dataEntryDAO;

    @Autowired
    public DataEntryServiceImpl(DataEntryDAO dataEntryDAO) {
        this.dataEntryDAO = dataEntryDAO;
    }

    @Override
    public void createDataEntry(String dataSetName, String value) {
        dataEntryDAO.createDataEntry(dataSetName, value);
    }
}
