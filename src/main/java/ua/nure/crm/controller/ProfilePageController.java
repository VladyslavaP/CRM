package ua.nure.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import ua.nure.crm.controller.form.UpdateDetailsForm;
import ua.nure.crm.controller.form.UpdateNameForm;
import ua.nure.crm.controller.util.ResourceNotFoundException;
import ua.nure.crm.controller.util.RoutingConstants;
import ua.nure.crm.controller.util.ModelViewConstants;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.service.EmployeeService;

import java.util.Optional;

import static ua.nure.crm.controller.util.ModelViewConstants.CURRENT_USER_PARAMETER;
import static ua.nure.crm.controller.util.ModelViewConstants.EMAIL_PARAMETER;

@Controller
@RequestMapping(RoutingConstants.ROOT)
public class ProfilePageController extends BasePageController {

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @RequestMapping(method = RequestMethod.GET, params = "!" + EMAIL_PARAMETER)
    public String getCurrentEmployeeProfilePage(Model model) {
        Employee currentEmployee = getCurrentEmployee(model);
        return getPageWithPopulatedData(model, currentEmployee);
    }

    @RequestMapping(method = RequestMethod.GET, params = EMAIL_PARAMETER)
    public String getAnotherEmployeeProfilePage(@RequestParam(required = true) String email, Model model) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(email);
        checkIfExists(employee);
        return getPageWithPopulatedData(model, employee.get());
    }

    @UpdateCurrentUser
    @RequestMapping(value= RoutingConstants.UPDATE_DETAILS, method = RequestMethod.POST)
    public String updateDetails(@RequestBody UpdateDetailsForm form, Model model) {
        Employee updatedEmployee = employeeService
                .updateEmployeeDetails(getCurrentEmployee(model).getId(), form);
        model.addAttribute(CURRENT_USER_PARAMETER, updatedEmployee);
        return ModelViewConstants.DETAILS_FRAGMENT;
    }

    @UpdateCurrentUser
    @RequestMapping(value= RoutingConstants.UPDATE_NAME, method = RequestMethod.POST)
    public String updateNAme(@RequestBody UpdateNameForm form, Model model) {
        Employee updatedEmployee = employeeService
                .updateEmployeeNameAndPosition(getCurrentEmployee(model).getId(), form);
        model.addAttribute(CURRENT_USER_PARAMETER, updatedEmployee);
        return ModelViewConstants.NAME_FRAGMENT;
    }

    private void checkIfExists(Optional<Employee> employee) {
        if(!employee.isPresent())
            throw new ResourceNotFoundException();
    }

    private void populateModelWithEmployeeData(Employee employee, Model model) {

    }

    private String getPageWithPopulatedData(Model model, Employee currentEmployee) {
        populateModelWithEmployeeData(currentEmployee, model);
        return ModelViewConstants.PROFILE_PAGE;
    }


}
