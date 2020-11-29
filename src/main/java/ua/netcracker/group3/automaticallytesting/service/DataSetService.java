package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.DataSet;

import java.util.List;

public interface DataSetService {
    void createDataSet(String name);

    List<DataSet> getAll();
}
