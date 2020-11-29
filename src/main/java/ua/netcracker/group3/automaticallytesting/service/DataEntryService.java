package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.util.List;

public interface DataEntryService {
    void createDataEntry(String dataSetName, String value);

    List<DataEntry> getAllByDataSetId(Long dataSetId);
}
