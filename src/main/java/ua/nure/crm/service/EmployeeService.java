package ua.nure.crm.service;

import ua.nure.crm.controller.form.UpdateDetailsForm;
import ua.nure.crm.controller.form.UpdateNameForm;
import ua.nure.crm.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService{

    Optional<Employee> getEmployeeByEmail(String email);

    Employee updateEmployeeDetails(Long employeeId, UpdateDetailsForm form);

    Employee updateEmployeeNameAndPosition(Long employeeId, UpdateNameForm form);

    List<Employee> getAllEmployees();
}
