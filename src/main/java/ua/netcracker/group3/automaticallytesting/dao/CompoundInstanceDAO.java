package ua.netcracker.group3.automaticallytesting.dao;

import ua.netcracker.group3.automaticallytesting.dto.TestScenarioItemDto;

public interface CompoundInstanceDAO {
    long saveCompoundInstanceAndGetGeneratedId(TestScenarioItemDto dto, long testScenarioId);
}
