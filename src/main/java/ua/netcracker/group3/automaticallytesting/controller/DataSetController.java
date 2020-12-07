package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;
import ua.netcracker.group3.automaticallytesting.model.DataSet;
import ua.netcracker.group3.automaticallytesting.service.DataEntryService;
import ua.netcracker.group3.automaticallytesting.service.DataSetService;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class DataSetController {

    private DataSetService dataSetService;
    private DataEntryService dataEntryService;

    @Autowired
    public DataSetController(DataSetService dataSetService,DataEntryService dataEntryService){
        this.dataSetService = dataSetService;
        this.dataEntryService = dataEntryService;
    }

    @GetMapping("/allDataSet")
    public List<DataSet> getAllDataSet() {
        return dataSetService.gettAllDataSet();
    }

    @RequestMapping(value = "/dataset/edit/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public DataSet getDataSetByIdForEdit(@PathVariable Integer id){
        return dataSetService.getDataSetById(id);
    }

    @RequestMapping(value = "/dataentry/edit/{dataSetId}",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public List<DataEntry> getDataEntry(@PathVariable Integer dataSetId){
        return dataEntryService.getDataEntryByDataSetName(dataSetId);
    }

    @RequestMapping(value = "/dataset/edit/{id}/{name}/update",method = RequestMethod.PUT)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public ResponseEntity<?> updateDataEntryById(@PathVariable Long id, @PathVariable String name, @RequestBody List<DataEntry> dataEntryList){
        DataSet editedDataSet = DataSet.builder().id(id).name(name).build();
        dataSetService.updateDataSet(editedDataSet);
        dataEntryService.updateDataEntry(dataEntryList);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @RequestMapping(value = "/dataset/edit/{dataEntryId}/delete",method = RequestMethod.DELETE)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public String deleteDataEntryById(@PathVariable Integer dataEntryId){
        dataEntryService.deleteDataEntryValueById(dataEntryId);
        return "ok";
    }


    @PostMapping("/create-data-set/{name}")
    public void createDataSet(@PathVariable("name") String name,
                              @RequestBody List<DataEntry> dataSetValues) {
        long id = dataSetService.createDataSet(name);
        dataEntryService.createDataEntry(id, dataSetValues);
    }

    @DeleteMapping("/delete-data-set/{id}")
    public int deleteDataSet(@PathVariable("id") long id) {
        dataEntryService.deleteDataEntry(id);
        return  dataSetService.deleteDataSet(id);
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
