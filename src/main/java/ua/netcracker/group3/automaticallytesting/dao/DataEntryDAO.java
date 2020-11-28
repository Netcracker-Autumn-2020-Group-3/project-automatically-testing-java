package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.util.List;

public interface DataEntryDAO {
    List<DataEntry> getDataEntryByDataSetName(Integer dataSetId);
}
