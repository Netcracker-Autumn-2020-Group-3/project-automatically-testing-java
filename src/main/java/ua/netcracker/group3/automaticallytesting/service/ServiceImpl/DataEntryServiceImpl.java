package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.DataEntryDAO;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.service.DataEntryService;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataEntryServiceImpl implements DataEntryService {

    private DataEntryDAO dataEntryDAO;

    @Autowired
    public DataEntryServiceImpl(DataEntryDAO dataEntryDAO) {
        this.dataEntryDAO = dataEntryDAO;
    }

    @Override
    public void createDataEntry(Long dataSetId, List<DataEntry> dataSetValues) {
        dataEntryDAO.createDataEntry(dataSetId, dataSetValues);
    }

    @Override
    public void deleteDataEntry(long dataSetId) {
        dataEntryDAO.deleteDataEntry(dataSetId);
    }

    @Override
    public List<DataEntry> getDataEntryByDataSetName(Integer dataSetId) {
        return dataEntryDAO.getDataEntryByDataSetName(dataSetId);
    }

    @Override
    public void updateDataEntry(List<DataEntry> dataEntryList) {
        List<DataEntry> dataEntryForUpdate = dataEntryList.stream().filter(d -> d.getId() != null).collect(Collectors.toList());
        List<DataEntry> dataEntryForInsert = dataEntryList.stream().filter(d -> d.getId() == null).collect(Collectors.toList());
        dataEntryDAO.createDataEntry(dataEntryForInsert);
        dataEntryDAO.updateDataEntry(dataEntryForUpdate);
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
