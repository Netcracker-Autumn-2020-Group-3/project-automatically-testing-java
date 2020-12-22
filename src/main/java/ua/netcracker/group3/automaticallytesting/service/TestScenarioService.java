package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDto;
import ua.netcracker.group3.automaticallytesting.dto.TestScenarioDtoWithIdNameArchived;
import ua.netcracker.group3.automaticallytesting.model.CompoundActionWithActionIdAndPriority;
import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

public interface TestScenarioService {

    boolean updateTestScenario(TestScenarioDtoWithIdNameArchived testScenario);

    boolean saveTestScenario(TestScenarioDto testScenarioDto);

    List<TestScenario> getTestScenarios(Pageable pageable, String name);

    List<CompoundActionWithActionIdAndPriority> getAllCompoundActionsByCompoundId(long id);

    List<TestScenario> getTestScenarioById(long id);

    List<TestScenario> getAll();

    Integer countPages(Integer pageSize);


    //     List<User> getUsers(Pageable pageable, String name, String surname, String email, String role);
}
