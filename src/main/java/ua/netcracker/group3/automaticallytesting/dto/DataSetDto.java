package ua.netcracker.group3.automaticallytesting.dto;

import lombok.Data;
import ua.netcracker.group3.automaticallytesting.model.DataEntry;

import java.util.List;

@Data
public class DataSetDto {
    String dataSetName;
    List<DataEntry> dataEntryValues;
}
