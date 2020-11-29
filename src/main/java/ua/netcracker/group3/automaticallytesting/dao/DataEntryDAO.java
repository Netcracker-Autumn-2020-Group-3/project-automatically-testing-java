package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.util.List;

public interface DataEntryDAO {
    void createDataEntry(String dataSetName, String value);

    List<DataEntry> getAllByDataSetId(Long dataSetId);
}
