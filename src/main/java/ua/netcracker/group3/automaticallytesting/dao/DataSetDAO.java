package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.DataSet;

import java.util.List;

public interface DataSetDAO {
    DataSet getDataSetById(Integer dataSetId);

    void updateDataSet(DataSet editedDataSet);
    void createDataSet(String name);

    List<DataSet> getAll();
}
