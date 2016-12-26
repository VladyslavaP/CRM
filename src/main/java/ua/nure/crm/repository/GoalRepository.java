package ua.nure.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.crm.entity.Goal;

public interface GoalRepository extends JpaRepository<Goal, Long>{
}
