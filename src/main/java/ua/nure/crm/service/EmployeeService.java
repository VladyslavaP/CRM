package ua.nure.crm.service;

import ua.nure.crm.entity.Employee;

import java.util.Optional;

public interface EmployeeService{

    Optional<Employee> getEmployeeByEmail(String email);

}
