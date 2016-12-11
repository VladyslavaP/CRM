package ua.nure.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.nure.crm.controller.util.ModelViewConstants;
import ua.nure.crm.controller.util.RoutingConstants;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.service.EmployeeService;

import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;
import static ua.nure.crm.controller.util.ModelViewConstants.COWORKERS_PARAMETER;

@Controller
@RequestMapping(RoutingConstants.COWORKERS)
public class CoworkersPageController extends BasePageController{

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getCoworkersList(Model model) {
        model.addAttribute(COWORKERS_PARAMETER, getCoworkers(getCurrentEmployee(model)));
        return ModelViewConstants.COWORKERS_PAGE;
    }

    private List<Employee> getCoworkers(Employee currentEmployee) {
        return filterOutCurrentEmployee(employeeService.getAllEmployees(), currentEmployee);
    }

    private List<Employee> filterOutCurrentEmployee(List<Employee> allEmployees, Employee currentEmployee) {
        return allEmployees.stream().filter(isNotCurrentEmployee(currentEmployee)).collect(toList());
    }

    private Predicate<? super Employee> isNotCurrentEmployee(Employee currentEmployee) {
        return employee -> !currentEmployee.getId().equals(employee.getId());
    }
}
