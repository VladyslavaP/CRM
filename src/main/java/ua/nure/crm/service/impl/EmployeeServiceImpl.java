package ua.nure.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.crm.controller.form.UpdateDetailsForm;
import ua.nure.crm.controller.form.UpdateNameForm;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.repository.EmployeeRepository;
import ua.nure.crm.service.EmployeeService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.springframework.util.StringUtils.hasText;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repository;

    @Autowired
    public void setRepository(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<Employee> getEmployeeByEmail(String email) {
        return ofNullable(repository.findOneByEmail(email));
    }

    @Override
    public Employee updateEmployeeDetails(Long employeeId, UpdateDetailsForm form) {
        Employee employeeToUpdate = repository.findOne(employeeId);
        employeeToUpdate.setPhoneNumber(getRealStringValue(form.getPhoneNumber()));
        employeeToUpdate.setBirthDate(parseBirthDate(form));
        return repository.save(employeeToUpdate);
    }

    @Override
    public Employee updateEmployeeNameAndPosition(Long employeeId, UpdateNameForm form) {
        Employee employeeToUpdate = repository.findOne(employeeId);
        employeeToUpdate.setFirstName(getRealStringValue(form.getFirstName()));
        employeeToUpdate.setLastName(getRealStringValue(form.getLastName()));
        employeeToUpdate.setPosition(getRealStringValue(form.getPosition()));
        return repository.save(employeeToUpdate);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long userId) {
        return ofNullable(repository.findOne(userId));
    }

    @Override
    public void updateEmployeePhoto(Long employeeId, String photoUrl) {
        Employee employeeToUpdate = repository.findOne(employeeId);
        employeeToUpdate.setPhotoUrl(photoUrl);
        repository.save(employeeToUpdate);
    }

    private String getRealStringValue(String value) {
        return hasText(value) ? value : null;
    }

    private LocalDate parseBirthDate(UpdateDetailsForm form) {
        LocalDate result = null;
        if (hasText(form.getBirthDate()))
            result = LocalDate.parse(form.getBirthDate());
        return result;
    }
}