package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.util.List;

public interface DataEntryService {
    List<DataEntry> getDataEntryByDataSetName(Integer dataSetId);

    void updateDataEntry(List<DataEntry> dataEntryList);

    void deleteDataEntryValueById(Integer dataEntryId);

    void createDataEntry(String dataSetName, String value);
}
