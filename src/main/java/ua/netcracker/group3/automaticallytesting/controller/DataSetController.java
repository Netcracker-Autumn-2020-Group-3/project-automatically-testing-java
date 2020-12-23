package ua.netcracker.group3.automaticallytesting.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.dto.DataSetDto;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import ua.netcracker.group3.automaticallytesting.service.DataEntryService;
import ua.netcracker.group3.automaticallytesting.service.DataSetService;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
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
     * @return
     */
    @GetMapping("/all-data-set")
    public List<DataSet> getAllDataSet() {
        log.info("get all data set");
        return dataSetService.getAllDataSet();
    }

    /**
     * @param id
     * @return
     */
    @GetMapping(value = "/dataset/edit/{id}")
    public DataSet getDataSetById(@PathVariable Integer id){
        return dataSetService.getDataSetById(id);
    }

    /**
     * @param dataSetId
     * @return
     */
    @GetMapping(value = "/dataentry/edit/{dataSetId}")
    public List<DataEntry> getDataEntry(@PathVariable Integer dataSetId){
        return dataEntryService.getDataEntryByDataSetName(dataSetId);
    }

    /**
     * @param id
     * @param name
     * @param dataEntryList
     * @return
     */
    @PutMapping(value = "/dataset/edit/{id}/{name}/update")
    public ResponseEntity<?> updateDataEntryById(@PathVariable Long id, @PathVariable String name, @RequestBody List<DataEntry> dataEntryList){
        DataSet editedDataSet = DataSet.builder().id(id).name(name).build();
        dataSetService.updateDataSet(editedDataSet);
        dataEntryService.updateDataEntry(dataEntryList);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /**
     * @param dataEntryId
     * @return
     */
    @DeleteMapping(value = "/dataset/edit/{dataEntryId}/delete")
    public String deleteDataEntryById(@PathVariable Integer dataEntryId){
        dataEntryService.deleteDataEntryValueById(dataEntryId);
        return "ok";
    }

    /**
     * @param dataSetValues
     */
    @PostMapping("/create-data-set")
    public void createDataSet(@RequestBody DataSetDto dataSetValues) {
        long id = dataSetService.createDataSet(dataSetValues.getDataSetName());
        log.info("created data set id: {}, values: {}", id, dataSetValues.getDataEntryValues());
        dataEntryService.createDataEntry(id, dataSetValues.getDataEntryValues());
    }

    /**
     * @param id
     * @return
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
