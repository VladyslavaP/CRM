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
public class LoginController {

    @RequestMapping(value = LOGIN, method = RequestMethod.GET)
    public String getLoginPage(@RequestParam Optional<String> error, Model model) {
        model.addAttribute(ERROR_PARAMETER, error);
        return LOGIN_PAGE;
    }

}
