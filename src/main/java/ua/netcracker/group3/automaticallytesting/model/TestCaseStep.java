package ua.netcracker.group3.automaticallytesting.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TestCaseStep {
    private TestCase testCase;
    private ActionInstanceJoined actionInstanceJoined;
    private DataEntry dataEntry;
}
