package ua.nure.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ua.nure.crm.controller.form.CreateUpdateGoalForm;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.entity.Goal;
import ua.nure.crm.repository.GoalRepository;
import ua.nure.crm.service.GoalService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
public class GoalServiceImpl implements GoalService {

    private GoalRepository repository;

    @Autowired
    public void setRepository(GoalRepository repository) {
        this.repository = repository;
    }

    @Override
    public Goal createGoal(Employee employee, CreateUpdateGoalForm form) {
        Goal goal = new Goal();
        goal.setProgress((short) 1);
        goal.setTitle(form.getTitle());
        goal.setDueDate(parseGoalDueDate(form));
        linkToEmployee(goal, employee);
        repository.save(goal);
        linkToParentGoal(goal, employee, form).ifPresent(this::recalculateParentProgress);
        return goal;
    }

    private void linkToEmployee(Goal goal, Employee employee) {
        goal.setEmployee(employee);
        employee.getGoals().add(goal);
    }

    private Optional<Goal> linkToParentGoal(Goal goal, Employee employee, CreateUpdateGoalForm form) {
        if (form.getParent() != null) {
            Goal parentGoal = findEmployeeGoal(employee, form.getParent());
            parentGoal.getChildGoals().add(goal);
            goal.setParentGoal(parentGoal);
            return of(parentGoal);
        }
        return empty();
    }

    private Goal findEmployeeGoal(Employee employee, Long parentGoalId) {
        return employee.getGoals().stream().filter(goal -> parentGoalId.equals(goal.getId())).findFirst().get();
    }

    @Override
    public void deleteGoal(Employee employee, Long goalId) {
        Goal goalToDelete = repository.getOne(goalId);
        unlinkFromEmployee(goalToDelete, employee);
        unlinkFromParentGoal(goalToDelete, employee).ifPresent(this::recalculateParentProgress);
        repository.delete(goalToDelete);
    }

    private Optional<Goal> unlinkFromParentGoal(Goal goalToDelete, Employee employee) {
        Optional<Goal> parentGoal = ofNullable(goalToDelete.getParentGoal());
        parentGoal.ifPresent(parent -> {
            findEmployeeGoal(employee, parent.getId()).getChildGoals()
                    .removeIf(goal -> goalToDelete.getId().equals(goal.getId()));
            goalToDelete.setParentGoal(null);
            repository.save(parent);
        });
        return parentGoal;
    }

    private void unlinkFromEmployee(Goal goalToDelete, Employee employee) {
        employee.getGoals().removeIf(goal -> goalToDelete.getId().equals(goal.getId()));
        goalToDelete.setEmployee(null);
    }

    @Override
    public Goal updateGoal(Long goalId, CreateUpdateGoalForm form) {
        Goal goalToUpdate = repository.findOne(goalId);
        goalToUpdate.setTitle(form.getTitle());
        goalToUpdate.setDueDate(parseGoalDueDate(form));
        return repository.save(goalToUpdate);
    }

    @Override
    public Goal updateGoalProgress(Long goalId, Short progress) {
        Goal goalToUpdate = repository.findOne(goalId);
        goalToUpdate.setProgress(progress);
        ofNullable(goalToUpdate.getParentGoal()).ifPresent(this::recalculateParentProgress);
        return repository.save(goalToUpdate);
    }

    private void recalculateParentProgress(Goal parentGoal) {
        if (hasChildGoals(parentGoal)) {
            List<Goal> childGoals = parentGoal.getChildGoals();
            double proportion = 1d / childGoals.size();
            double sum = childGoals.stream().mapToDouble(goal -> goal.getProgress() * proportion).sum();
            parentGoal.setProgress((short) Math.round(sum));
            repository.save(parentGoal);
        }
    }

    private boolean hasChildGoals(Goal parentGoal) {
        return !CollectionUtils.isEmpty(parentGoal.getChildGoals());
    }

    private LocalDate parseGoalDueDate(CreateUpdateGoalForm form) {
        return LocalDate.parse(form.getDueDate());
    }
}
