package ua.nure.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.nure.crm.controller.form.CreateEventForm;
import ua.nure.crm.controller.form.UpdateEventForm;
import ua.nure.crm.controller.util.ModelViewConstants;
import ua.nure.crm.controller.util.RoutingConstants;
import ua.nure.crm.entity.Employee;
import ua.nure.crm.entity.Event;
import ua.nure.crm.service.EmployeeService;
import ua.nure.crm.service.EventService;

import javax.ws.rs.BadRequestException;
import java.util.Optional;

@Controller
@RequestMapping(RoutingConstants.EVENTS)
public class EventController {

    private EmployeeService employeeService;

    private EventService eventService;

    @Autowired
    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @UpdateCurrentUser
    @PutMapping
    public ResponseEntity<Event> createEvent(@RequestBody CreateEventForm form,
                                             @PathVariable(ModelViewConstants.USER_ID_PARAMETER) Long userId) {
        try{
            Optional<Employee> employee = getEmployee(userId);
            Event event = eventService.createEvent(employee.get(), form);
            return ResponseEntity.ok(event);
        } catch (Exception e){
            throw new BadRequestException();
        }
    }

    private Optional<Employee> getEmployee(Long userId) {
        Optional<Employee> employee = employeeService.getEmployeeById(userId);
        checkThatEmployeeExists(employee);
        return employee;
    }

    private void checkThatEmployeeExists(Optional<Employee> employee) {
        if(!employee.isPresent())
            throw new BadRequestException();
    }

    @UpdateCurrentUser
    @DeleteMapping(value = RoutingConstants.EVENT_ID)
    public ResponseEntity<Void> deleteEvent(@PathVariable(ModelViewConstants.EVENT_ID_PATH_PARAMETER) Long eventId){
        try{
            eventService.deleteEvent(eventId);
            return ResponseEntity.ok().build();
        } catch (Exception e){
            throw new BadRequestException();
        }
    }

    @UpdateCurrentUser
    @RequestMapping(value = RoutingConstants.EVENT_ID, method = RequestMethod.POST)
    public ResponseEntity<Event> updateEvent(@RequestBody UpdateEventForm form,
                                            @PathVariable(ModelViewConstants.EVENT_ID_PATH_PARAMETER) Long eventId){
        try{
            Event updatedEvent = eventService.updateEvent(eventId, form);
            return ResponseEntity.ok(updatedEvent);
        } catch (Exception e){
            throw new BadRequestException();
        }
    }
}
