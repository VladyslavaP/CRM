package ua.nure.crm.service;

import ua.nure.crm.controller.form.CreateUpdateGoalForm;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.entity.Goal;

public interface GoalService {

    Goal createGoal(Employee employee, CreateUpdateGoalForm form);

    void deleteGoal(Employee employee, Long goalId);

    Goal updateGoal(Long goalId, CreateUpdateGoalForm form);

    Goal updateGoalProgress(Long goalId, Short progress);
}
