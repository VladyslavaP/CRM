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
import ua.nure.crm.service.PhotoService;

import java.util.Optional;

import static ua.nure.crm.controller.util.ModelViewConstants.CURRENT_USER_PARAMETER;
import static ua.nure.crm.controller.util.ModelViewConstants.EMAIL_PARAMETER;

@Controller
@RequestMapping(RoutingConstants.ROOT)
public class ProfilePageController extends BasePageController {

    private EmployeeService employeeService;

    private PhotoService photoService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setPhotoService(PhotoService photoService) {
        this.photoService = photoService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping(params = "!" + EMAIL_PARAMETER)
    public String getCurrentEmployeeProfilePage(Model model) {
        Employee currentEmployee = getCurrentEmployee(model);
        return getPageWithPopulatedData(model, currentEmployee);
    }

    @GetMapping(params = EMAIL_PARAMETER)
    public String getAnotherEmployeeProfilePage(@RequestParam(required = true) String email, Model model) {
        return getPageWithPopulatedData(model, getEmployeeByEmail(email));
    }

    private Employee getEmployeeByEmail(String email) {
        Optional<Employee> employee = employeeService.getEmployeeByEmail(email);
        checkIfExists(employee);
        return employee.get();
    }

    @UpdateCurrentUser
    @PostMapping(value= RoutingConstants.UPDATE_DETAILS)
    public String updateDetails(@RequestBody UpdateDetailsForm form, Model model) {
        Employee updatedEmployee = employeeService
                .updateEmployeeDetails(getCurrentEmployee(model).getId(), form);
        model.addAttribute(CURRENT_USER_PARAMETER, updatedEmployee);
        populateModelWithEmployeeData(updatedEmployee, model);
        return ModelViewConstants.DETAILS_FRAGMENT;
    }

    @PostMapping(value= RoutingConstants.UPDATE_NAME)
    public String updateNAme(@RequestBody UpdateNameForm form, Model model) {
        Employee updatedEmployee = employeeService
                .updateEmployeeNameAndPosition(getEmployeeByEmail(form.getEmail()).getId(), form);
        populateModelWithEmployeeData(updatedEmployee, model);
        model.addAttribute(CURRENT_USER_PARAMETER, updatedEmployee);
        return ModelViewConstants.NAME_FRAGMENT;
    }

    private void checkIfExists(Optional<Employee> employee) {
        if(!employee.isPresent())
            throw new ResourceNotFoundException();
    }

    private void populateModelWithEmployeeData(Employee employee, Model model) {
        model.addAttribute(ModelViewConstants.PROFILE_USER_PARAMETER, employee);
    }

    private String getPageWithPopulatedData(Model model, Employee employee) {
        populateModelWithEmployeeData(employee, model);
        return ModelViewConstants.PROFILE_PAGE;
    }


}
