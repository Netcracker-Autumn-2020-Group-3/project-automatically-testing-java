package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.DataSet;

import java.util.List;

public interface DataSetService {

    DataSet getDataSetById(Integer id);
    void updateDataSet(DataSet editedDataset);
    void createDataSet(String name);

    List<DataSet> gettAllDataSet();
    List<DataSet> getAll();
}
