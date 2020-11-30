package ua.netcracker.group3.automaticallytesting.service.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.netcracker.group3.automaticallytesting.dao.DataSetDAO;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import ua.netcracker.group3.automaticallytesting.service.DataSetService;

import java.util.List;

@Service
public class DataSetServiceImpl implements DataSetService {

    DataSetDAO dataSetDAO;

    @Autowired
    public DataSetServiceImpl(DataSetDAO dataSetDAO) {
        this.dataSetDAO = dataSetDAO;
    }

    @Override
    public void createDataSet(String name) {
        dataSetDAO.createDataSet(name);
    }

    @Override
    public List<DataSet> gettAllDataSet() {
        return dataSetDAO.getAllDataSet();
    }
}
