package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.DataEntryDAO;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.service.DataEntryService;
import java.util.List;

@Service
public class DataEntryServiceImpl implements DataEntryService {

    private DataEntryDAO dataEntryDAO;

    @Autowired
    public DataEntryServiceImpl(DataEntryDAO dataEntryDAO) {
        this.dataEntryDAO = dataEntryDAO;
    }

    @Override
    public void createDataEntry(String dataSetName, String value) {
        dataEntryDAO.createDataEntry(dataSetName, value);
    }

    @Override
    public List<DataEntry> getDataEntryByDataSetName(Integer dataSetId) {
        return dataEntryDAO.getDataEntryByDataSetName(dataSetId);
    }

    @Override
    public void updateDataEntry(List<DataEntry> dataEntryList) {
        dataEntryDAO.updateDataEntry(dataEntryList);
    }

    @Override
    public void deleteDataEntryValueById(Integer dataEntryId) {
        dataEntryDAO.deleteDataEntryValueById(dataEntryId);
    }

    @Override
    public List<DataEntry> getAllByDataSetId(Long dataSetId){
        return dataEntryDAO.getAllByDataSetId(dataSetId);
    }
}
