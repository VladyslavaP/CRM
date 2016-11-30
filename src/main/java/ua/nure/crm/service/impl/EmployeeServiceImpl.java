package ua.nure.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.repository.EmployeeRepository;
import ua.nure.crm.service.EmployeeService;

import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeRepository repository;

    @Autowired
    public void setRepository(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Employee> getEmployeeByEmail(String email) {
        return ofNullable(repository.findOneByEmail(email));
    }
}