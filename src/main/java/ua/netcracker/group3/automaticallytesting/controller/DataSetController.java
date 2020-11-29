package ua.netcracker.group3.automaticallytesting.controller;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import ua.netcracker.group3.automaticallytesting.service.DataEntryService;
import ua.netcracker.group3.automaticallytesting.service.DataSetService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class DataSetController {

    DataSetService dataSetService;
    DataEntryService dataEntryService;

    @Autowired
    public DataSetController(DataSetService dataSetService, DataEntryService dataEntryService) {
        this.dataSetService = dataSetService;
        this.dataEntryService = dataEntryService;
    }

    @PutMapping("/createDataSet")
    public void createDataSet(@RequestBody DataSet dataSet) {
        dataSetService.createDataSet(dataSet.getName());

    }

    @PutMapping("/createDataEntry/{name}")
    public void createDataEntry(@PathVariable("name") String name,
                                @RequestBody DataEntry dataEntry) {
        dataEntryService.createDataEntry(name, dataEntry.getValue());
    }

    @GetMapping("/data-set/list")
    public List<DataSet> getAllDatasets(){
        return dataSetService.getAll();
    }

    @GetMapping("/data-set/{id}/entries")
    public List<DataEntry> getDatasetEntries(@PathVariable("id") Long id){
        return dataEntryService.getAllByDataSetId(id);
    }
}
