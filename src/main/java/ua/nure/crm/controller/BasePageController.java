package ua.nure.crm.controller;

import org.springframework.ui.Model;
import ua.nure.crm.controller.util.ModelViewConstants;
import ua.nure.crm.entity.Employee;

public abstract class BasePageController {

    protected Employee getCurrentEmployee(Model model) {
        return (Employee) model.asMap().get(ModelViewConstants.CURRENT_USER_PARAMETER);
    }
}
