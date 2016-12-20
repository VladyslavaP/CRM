package ua.nure.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.nure.crm.controller.form.CreateEventForm;
import ua.nure.crm.controller.form.UpdateEventForm;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.entity.Event;
import ua.nure.crm.repository.EventRepository;
import ua.nure.crm.service.EventService;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.util.StringUtils.hasText;

@Service
public class EventServiceImpl implements EventService{

    private EventRepository eventRepository;

    @Autowired
    public void setEventRepository(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event createEvent(Employee employee, CreateEventForm form) {
        Event event = new Event();
        event.setEmployee(employee);
        event.setTitle(form.getTitle());
        event.setDate(parseEventDate(form));
        event.setTime(parseEventTime(form));
        Event savedEvent = eventRepository.save(event);
        employee.getEvents().add(savedEvent);
        return savedEvent;
    }

    @Override
    public void deleteEvent(Long eventId) {
        Event event = eventRepository.findOne(eventId);
        event.getEmployee().getEvents().remove(event);
        event.setEmployee(null);
        eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long eventId, UpdateEventForm form) {
        Event event = eventRepository.findOne(eventId);
        event.setTitle(form.getTitle());
        eventRepository.save(event);
        return event;
    }

    private LocalTime parseEventTime(CreateEventForm form) {
        LocalTime result = null;
        if (hasText(form.getTime()))
            result = LocalTime.parse(form.getTime());
        return result;
    }

    private LocalDate parseEventDate(CreateEventForm form) {
        LocalDate result = null;
        if (hasText(form.getDate()))
            result = LocalDate.parse(form.getDate());
        return result;
    }
}
