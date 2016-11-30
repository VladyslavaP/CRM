package ua.nure.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.nure.crm.entity.CurrentUser;
import ua.nure.crm.entity.User;
import ua.nure.crm.service.EmployeeService;

@Service
public class CurrentUserDetailsService implements UserDetailsService{

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public CurrentUser loadUserByUsername(String email) throws UsernameNotFoundException {
       User user = employeeService.getEmployeeByEmail(email).orElseThrow(
               () -> new UsernameNotFoundException(String.format("User with email=%s was not found", email)));
        return new CurrentUser(user);
    }
}
