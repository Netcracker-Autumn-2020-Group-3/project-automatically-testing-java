package ua.netcracker.group3.automaticallytesting.dao;


import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.util.List;

public interface DataEntryDAO {
    List<DataEntry> getDataEntryByDataSetName(Integer dataSetId);

    void updateDataEntry(List<DataEntry> dataEntryList);

    void deleteDataEntryValueById(Integer dataEntryId);

    void createDataEntry(Long dataSetId, List<DataEntry> dataSetValues);

    List<DataEntry> getAllByDataSetId(Long dataSetId);

    void deleteDataEntry(long dataSetId);
}
