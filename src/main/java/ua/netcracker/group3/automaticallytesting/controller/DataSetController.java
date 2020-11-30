package ua.netcracker.group3.automaticallytesting.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

    @RequestMapping(value = "/dataset/edit/{id}",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public DataSet getDataSetByIdForEdit(@PathVariable Integer id){
        return dataSetService.getDataSetById(id);
    }

    @RequestMapping(value = "/dataentry/edit/{dataSetId}",method = RequestMethod.GET)
    @PreAuthorize("hasAnyRole('ADMIN','MANAGER','ENGINEER')")
    public List<DataEntry> getDataEntryForEdit(@PathVariable Integer dataSetId){
        return dataEntryService.getDataEntryByDataSetName(dataSetId);
    }

    @RequestMapping(value = "/dataset/edit/{id}/{name}/update",method = RequestMethod.PUT)
    public String updateDataEntryById(@PathVariable Long id,@PathVariable String name,@RequestBody List<DataEntry> dataEntryList){
        DataSet editedDataSet = DataSet.builder().id(id).name(name).build();
        dataSetService.updateDataSet(editedDataSet);
        dataEntryService.updateDataEntry(dataEntryList);
        return "ok";
    }

    @RequestMapping(value = "/dataset/edit/{id}/{dataEntryId}/delete",method = RequestMethod.DELETE)
    public String deleteDataEntryById(@PathVariable Long id,@PathVariable Integer dataEntryId){
        dataEntryService.deleteDataEntryValueById(dataEntryId);
        return "ok";
    }





}
