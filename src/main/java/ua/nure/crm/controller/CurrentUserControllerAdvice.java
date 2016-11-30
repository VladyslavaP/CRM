package ua.nure.crm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.nure.crm.entity.CurrentUser;
import ua.nure.crm.entity.User;

import static java.util.Optional.ofNullable;
import static ua.nure.crm.controller.util.ModelViewConstants.CURRENT_USER_PARAMETER;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    @ModelAttribute(CURRENT_USER_PARAMETER)
    public User getCurrentEmployee(Authentication authentication) {
        return getEmployee(authentication);
    }

    private User getEmployee(Authentication authentication) {
        return ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(this::getCurrentUserDetails)
                .map(CurrentUser::getUser)
                .orElse(null);
    }

    private CurrentUser getCurrentUserDetails(Object principal) {
        return (CurrentUser) principal;
    }
}