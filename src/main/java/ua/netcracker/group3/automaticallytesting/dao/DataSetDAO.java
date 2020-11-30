package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.model.DataSet;

public interface DataSetDAO {
    DataSet getDataSetById(Integer dataSetId);

    void updateDataSet(DataSet editedDataSet);
}
