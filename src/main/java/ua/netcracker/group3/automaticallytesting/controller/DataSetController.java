package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.DataSetDto;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import ua.netcracker.group3.automaticallytesting.service.DataEntryService;
import ua.netcracker.group3.automaticallytesting.service.DataSetService;
import java.util.List;

@Slf4j
@CrossOrigin(origins = "https://automatically-testing-angular.herokuapp.com")
@RestController
public class DataSetController {

    private final DataSetService dataSetService;
    private final DataEntryService dataEntryService;

    @Autowired
    public DataSetController(DataSetService dataSetService,DataEntryService dataEntryService){
        this.dataSetService = dataSetService;
        this.dataEntryService = dataEntryService;
    }

    /**
     * @return list of data set
     */
    @GetMapping("/all-data-set")
    public List<DataSet> getAllDataSet() {
        log.info("get all data set");
        return dataSetService.getAllDataSet();
    }

    /**
     * Returns DataSet by dataSet Id
     * @param id needed for getting value from DB
     * @return DataSet
     */
    @GetMapping(value = "/dataset/edit/{id}")
    public DataSet getDataSetById(@PathVariable Integer id){
        return dataSetService.getDataSetById(id);
    }

    /**
     * Returns the list of DataEntry by dataSetId
     * @param dataSetId needed for getting value from DB
     * @return list of DataEntry
     */
    @GetMapping(value = "/dataentry/edit/{dataSetId}")
    public List<DataEntry> getDataEntry(@PathVariable Integer dataSetId){
        return dataEntryService.getDataEntryByDataSetName(dataSetId);
    }

    /**
     * Method updates DataSet by Id and name
     * Method updates DataEntry using dataEntryList
     * Returns status OK if method updates values successfully
     * @param id needed for updating dataSet in DB
     * @param name needed for updating dataSet in DB
     * @param dataEntryList needed for updating dataEnrty in DB
     * @return ResponseEntity with status OK
     */
    @PutMapping(value = "/dataset/edit/update/{id}/{name}")
    public ResponseEntity<?> updateDataEntryById(@PathVariable Long id,
                                                 @PathVariable String name, @RequestBody List<DataEntry> dataEntryList){
        DataSet editedDataSet = DataSet.builder().id(id).name(name).build();
        dataSetService.updateDataSet(editedDataSet);
        dataEntryService.updateDataEntry(dataEntryList);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * Returns status OK if method deletes values successfully
     * @param dataEntryId needed for deleting value from DB
     * @return ResponseEntity with status OK
     */
    @DeleteMapping(value = "/dataset/edit/delete/{dataEntryId}")
    public ResponseEntity<?> deleteDataEntryById(@PathVariable Integer dataEntryId){
        dataEntryService.deleteDataEntryValueById(dataEntryId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * create data set with data entry
     * @param dataSetValues data set name, data entry key, value
     */
    @PostMapping("/create-data-set")
    public void createDataSet(@RequestBody DataSetDto dataSetValues) {
        long id = dataSetService.createDataSet(dataSetValues.getDataSetName());
        log.info("created data set id: {}, values: {}", id, dataSetValues.getDataEntryValues());
        dataEntryService.createDataEntry(id, dataSetValues.getDataEntryValues());
    }

    /**
     * archived data set
     * @param id of data set
     */
    @PatchMapping("/delete-data-set/{id}")
    public void deleteDataSet(@PathVariable("id") long id) {
        log.info("delete data set: id = {}",id);
        dataSetService.deleteDataSet(id);
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
