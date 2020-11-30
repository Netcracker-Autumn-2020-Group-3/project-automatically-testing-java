package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.DataSet;

public interface DataSetService {
    DataSet getDataSetById(Integer id);
    void updateDataSet(DataSet editedDataset);
}
