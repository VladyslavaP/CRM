package ua.nure.crm.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.nure.crm.controller.util.ModelViewConstants;
import ua.nure.crm.controller.util.RoutingConstants;
import ua.nure.crm.entity.Employee;

public abstract class BasePageController{

    private static final String NO_PHOTO_URL = RoutingConstants.PHOTOS + RoutingConstants.NO_PHOTO;

    @ModelAttribute(ModelViewConstants.NO_PHOTO_URL)
    public String getNoPhotoUrl() {
        return NO_PHOTO_URL;
    }

    protected Employee getCurrentEmployee(Model model) {
        return (Employee) model.asMap().get(ModelViewConstants.CURRENT_USER_PARAMETER);
    }
}
