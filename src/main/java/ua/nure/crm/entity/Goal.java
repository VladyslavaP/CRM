package ua.nure.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "goals")
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "due_date", nullable = false, columnDefinition = "DATE")
    private LocalDate dueDate;

    @Column(name = "progress", nullable = false)
    private Short progress;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Employee employee;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "parent", nullable = true)
    private Goal parentGoal;

    @OneToMany(mappedBy = "parentGoal", fetch = FetchType.EAGER)
    @OrderBy("id ASC")
    private List<Goal> childGoals;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Short getProgress() {
        return progress;
    }

    public void setProgress(Short progress) {
        this.progress = progress;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Goal getParentGoal() {
        return parentGoal;
    }

    public void setParentGoal(Goal parentGoal) {
        this.parentGoal = parentGoal;
    }

    public List<Goal> getChildGoals() {
        return childGoals;
    }

    public void setChildGoals(List<Goal> childGoals) {
        this.childGoals = childGoals;
    }
}
