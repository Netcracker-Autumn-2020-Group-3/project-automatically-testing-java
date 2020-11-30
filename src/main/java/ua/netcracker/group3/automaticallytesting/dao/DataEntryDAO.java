package ua.netcracker.group3.automaticallytesting.dao;

public interface DataEntryDAO {
    void createDataEntry(String dataSetName, String value);
import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.util.List;

public interface DataEntryDAO {
    List<DataEntry> getDataEntryByDataSetName(Integer dataSetId);

    void updateDataEntry(List<DataEntry> dataEntryList);

    void deleteDataEntryValueById(Integer dataEntryId);
}
