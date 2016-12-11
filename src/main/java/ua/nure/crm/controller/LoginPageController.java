package ua.nure.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

import static ua.nure.crm.controller.util.RoutingConstants.LOGIN;
import static ua.nure.crm.controller.util.ModelViewConstants.ERROR_PARAMETER;
import static ua.nure.crm.controller.util.ModelViewConstants.LOGIN_PAGE;

@Controller
public class LoginPageController {

    @RequestMapping(value = LOGIN, method = RequestMethod.GET)
    public String getLoginPage(@RequestParam Optional<String> error, Model model) {
        populateModelIfLoginFailed(error, model);
        return LOGIN_PAGE;
    }

    private void populateModelIfLoginFailed(Optional<String> error, Model model) {
        if(error.isPresent())
            model.addAttribute(ERROR_PARAMETER, error.get());
    }

}
