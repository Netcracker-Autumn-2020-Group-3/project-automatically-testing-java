package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.DataSetDAO;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import ua.netcracker.group3.automaticallytesting.service.DataSetService;

import java.util.List;

@Service
public class DataSetServiceImpl implements DataSetService {

    private DataSetDAO dataSetDAO;

    @Autowired
    public DataSetServiceImpl(DataSetDAO dataSetDAO){
        this.dataSetDAO = dataSetDAO;
    }

    @Override
    public DataSet getDataSetById(Integer dataSetId) {
        return dataSetDAO.getDataSetById(dataSetId);
    }

    @Override
    public void updateDataSet(DataSet editedDataSet) {
        dataSetDAO.updateDataSet(editedDataSet);
    }

    @Override
    public long createDataSet(String name) {
        return dataSetDAO.createDataSet(name);
    }

    @Override
    public int deleteDataSet(long id) {
        return dataSetDAO.deleteDataSet(id);
    }

    @Override
    public List<DataSet> gettAllDataSet() {
        return dataSetDAO.getAllDataSet();
    }

    @Override
    public List<DataSet> getAll(){
        return dataSetDAO.getAll();
    }
}
