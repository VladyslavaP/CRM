package ua.nure.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import ua.nure.crm.entity.CurrentUser;
import ua.nure.crm.entity.User;
import ua.nure.crm.service.EmployeeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static ua.nure.crm.controller.util.ModelViewConstants.CURRENT_USER_PARAMETER;

@ControllerAdvice
public class CurrentUserControllerAdvice {

    @ModelAttribute(CURRENT_USER_PARAMETER)
    public User getCurrentEmployee(Authentication authentication) {
        return getEmployee(authentication);
    }

    private static User getEmployee(Authentication authentication) {
        return getCurrentUser(authentication)
                .map(CurrentUser::getUser)
                .orElse(null);
    }

    private static Optional<CurrentUser> getCurrentUser(Authentication authentication) {
        return ofNullable(authentication)
                .map(Authentication::getPrincipal)
                .map(CurrentUserControllerAdvice::getCurrentUserDetails);
    }

    private static CurrentUser getCurrentUserDetails(Object principal) {
        return (CurrentUser) principal;
    }

    public static class CurrentUserUpdateInterceptor implements HandlerInterceptor {

        private EmployeeService employeeService;

        @Autowired
        public void setEmployeeService(EmployeeService employeeService) {
            this.employeeService = employeeService;
        }

        @Override
        public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler, ModelAndView
                modelAndView) throws Exception {
            if (isUserUpdated((HandlerMethod) handler))
                refreshCurrentUser();
        }

        private boolean isUserUpdated(HandlerMethod handler) {
            Method method = handler.getMethod();
            return method.isAnnotationPresent(UpdateCurrentUser.class);
        }

        private void refreshCurrentUser() {
            createNewPrincipal()
                .map(this::createAuthentication)
                .ifPresent(SecurityContextHolder.getContext()::setAuthentication);
        }

        private Authentication createAuthentication(CurrentUser currentUser) {
            return new UsernamePasswordAuthenticationToken(currentUser, currentUser.getPassword(),
                    currentUser.getAuthorities());
        }

        private Optional<CurrentUser> createNewPrincipal() {
            return getCurrentUser(getAuthentication())
                    .map(CurrentUser::getUsername)
                    .flatMap(employeeService::getEmployeeByEmail)
                    .map(CurrentUser::new);
        }

        private Authentication getAuthentication() {
            return SecurityContextHolder.getContext().getAuthentication();
        }

        @Override
        public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        }
    }
}