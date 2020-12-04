package ua.netcracker.group3.automaticallytesting.service;

import ua.netcracker.group3.automaticallytesting.model.TestScenario;
import ua.netcracker.group3.automaticallytesting.util.Pageable;

import java.util.List;

public interface TestScenarioService {

    void updateTestScenario(TestScenario testScenario);

    void saveTestScenario(TestScenario testScenario);

    List<TestScenario> getTestScenarios(Pageable pageable, String name);

    List<TestScenario> getAll();


    //     List<User> getUsers(Pageable pageable, String name, String surname, String email, String role);
}
