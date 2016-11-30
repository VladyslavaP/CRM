package ua.nure.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.nure.crm.controller.util.RoutingConstants;
import ua.nure.crm.controller.util.ModelViewConstants;

@Controller
public class DashboardController {

    @RequestMapping(RoutingConstants.ROOT)
    public String getDashboardPage() {
        return ModelViewConstants.DASHBOARD_PAGE;
    }

}
