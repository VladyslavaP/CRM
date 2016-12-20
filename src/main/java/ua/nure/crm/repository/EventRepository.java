package ua.nure.crm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.nure.crm.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
}
