package ua.nure.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.nure.crm.controller.form.CreateUpdateGoalForm;
import ua.nure.crm.controller.util.ModelViewConstants;
import ua.nure.crm.controller.util.RoutingConstants;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.entity.Goal;
import ua.nure.crm.service.EmployeeService;
import ua.nure.crm.service.GoalService;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

@Controller
@RequestMapping("/users/{userId}/goals")
public class GoalController extends BasePageController{

    private EmployeeService employeeService;

    private GoalService goalService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setGoalService(GoalService goalService) {
        this.goalService = goalService;
    }

    @UpdateCurrentUser
    @PutMapping
    public String createGoal(@RequestBody CreateUpdateGoalForm form,
                             @PathVariable(ModelViewConstants.USER_ID_PARAMETER) Long userId,
                             Model model) {
        Goal goal = goalService.createGoal(findEmployee(userId), form);
        populateModelWithEmployeeData(findEmployee(userId), model);
        populateModelWithUpdatedParentGoalData(goal.getParentGoal(), model);
        return "profileGoals :: goals";
    }

    @UpdateCurrentUser
    @DeleteMapping(RoutingConstants.GOAL_ID)
    public String deleteGoal(@PathVariable(ModelViewConstants.GOAL_ID_PATH_PARAMETER) Long goalId,
                             @PathVariable(ModelViewConstants.USER_ID_PARAMETER) Long userId, Model model) {
        goalService.deleteGoal(findEmployee(userId), goalId);
        populateModelWithEmployeeData(findEmployee(userId), model);
        return "profileGoals :: goals";
    }

    @UpdateCurrentUser
    @PostMapping(RoutingConstants.GOAL_ID)
    public String updateGoal(@RequestBody CreateUpdateGoalForm form,
                             @PathVariable(ModelViewConstants.USER_ID_PARAMETER) Long userId,
                             @PathVariable(ModelViewConstants.GOAL_ID_PATH_PARAMETER) Long goalId,
                             Model model) {
        Goal goal = goalService.updateGoal(goalId, form);
        populateModelWithEmployeeData(findEmployee(userId), model);
        populateModelWithUpdatedParentGoalData(goal.getParentGoal(), model);
        return "profileGoals :: goals";
    }

    @UpdateCurrentUser
    @PostMapping(RoutingConstants.GOAL_ID + RoutingConstants.PROGRESS)
    public String updateGoalProgress(@PathVariable(ModelViewConstants.USER_ID_PARAMETER) Long userId,
                             @PathVariable(ModelViewConstants.GOAL_ID_PATH_PARAMETER) Long goalId,
                             @PathVariable(ModelViewConstants.PROGRESS_PATH_PARAMETER) Short progress,
                             Model model) {
        Goal goal = goalService.updateGoalProgress(goalId, progress);
        populateModelWithEmployeeData(findEmployee(userId), model);
        populateModelWithUpdatedParentGoalData(goal.getParentGoal(), model);
        return "profileGoals :: goals";
    }

    private void populateModelWithUpdatedParentGoalData(Goal parentGoal, Model model) {
        if(parentGoal != null)
            model.addAttribute(ModelViewConstants.UPDATED_PARENT_GOAL, parentGoal.getId());
    }

    private Employee findEmployee(@PathVariable(ModelViewConstants.USER_ID_PARAMETER) Long userId) {
        Optional<Employee> employee = employeeService.getEmployeeById(userId);
        if(employee.isPresent())
            return employee.get();
        throw new BadRequestException();
    }
}
