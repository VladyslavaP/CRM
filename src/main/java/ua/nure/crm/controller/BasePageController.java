package ua.nure.crm.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.nure.crm.controller.util.ModelViewConstants;
import ua.nure.crm.controller.util.RoutingConstants;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.entity.Goal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public abstract class BasePageController {

    private static final String NO_PHOTO_URL = RoutingConstants.PHOTOS + RoutingConstants.NO_PHOTO;

    @ModelAttribute(ModelViewConstants.NO_PHOTO_URL)
    public String getNoPhotoUrl() {
        return NO_PHOTO_URL;
    }

    protected Employee getCurrentEmployee(Model model) {
        return (Employee) model.asMap().get(ModelViewConstants.CURRENT_USER_PARAMETER);
    }

    protected void populateModelWithEmployeeData(Employee employee, Model model) {
        model.addAttribute(ModelViewConstants.PROFILE_USER_PARAMETER, employee);
        populateGoals(employee, model);
    }

    protected void populateGoals(Employee employee, Model model) {
        Map<Boolean, List<Goal>> groupedGoals = employee.getGoals().stream().collect(Collectors.groupingBy(this::isInProgress));
        model.addAttribute(ModelViewConstants.IN_PROGRESS_GOALS, filterOutChildGoals(groupedGoals.get(true)));
        model.addAttribute(ModelViewConstants.COMPLETED_GOALS, filterOutChildGoals(groupedGoals.get(false)));
    }

    protected boolean isInProgress(Goal goal) {
        return 100 > goal.getProgress();
    }

    protected List<Goal> filterOutChildGoals(List<Goal> goals) {
        List<Goal> onlyParentGoals = new ArrayList<>();
        if (goals != null)
            onlyParentGoals = goals.stream().filter(goal -> goal.getParentGoal() == null)
                    .collect(toList());
        return onlyParentGoals;
    }
}
