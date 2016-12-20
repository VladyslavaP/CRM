package ua.nure.crm.service;

import ua.nure.crm.controller.form.CreateEventForm;
import ua.nure.crm.controller.form.UpdateEventForm;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.entity.Event;

public interface EventService {

    Event createEvent(Employee employee, CreateEventForm form);

    void deleteEvent(Long eventId);

    Event updateEvent(Long eventId, UpdateEventForm form);
}
