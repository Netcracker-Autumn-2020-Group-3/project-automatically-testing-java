package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.DataSet;

import java.util.List;

public interface DataSetDAO {

    List<DataSet> getAllDataSet();
    void createDataSet(String name);
}
