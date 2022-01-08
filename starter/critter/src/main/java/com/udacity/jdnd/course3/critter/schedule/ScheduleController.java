package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.user.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {
        ScheduleDTO scheduleDTOResult;
        try {
            Schedule schedule = new Schedule(scheduleDTO.getDate(),scheduleDTO.getActivities());
            scheduleDTOResult = convertSchedule(scheduleService.saveSchedule(schedule, scheduleDTO.getEmployeeIds(), scheduleDTO.getPetIds()));
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving schedule", exception);
        }
        return scheduleDTOResult;

    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<ScheduleDTO> scheduleDTOResult = new ArrayList<>();
        try {
            List<Schedule> schedules = scheduleService.getAllSchedules();
            for (Schedule schedule : schedules) {
                scheduleDTOResult.add(convertSchedule(schedule));
            }
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting all schedules", exception);
        }
        return scheduleDTOResult;
    }


    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        try {
            List<Schedule> schedules = scheduleService.getScheduleForPet(petId);
            for (Schedule schedule : schedules) {
                scheduleDTOS.add(convertSchedule(schedule));
            }
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting schedules for pet", exception);
        }
        return scheduleDTOS;
    }


    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        try {
            List<Schedule> schedules = scheduleService.getScheduleForEmployee(employeeId);
            for (Schedule schedule : schedules) {
                scheduleDTOS.add(convertSchedule(schedule));
            }
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting schedules for employee", exception);
        }
        return scheduleDTOS;
    }


    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {
        List<ScheduleDTO> scheduleDTOS = new ArrayList<>();
        try {
            List<Schedule> schedules = scheduleService.getScheduleForCustomer(customerId);
            for (Schedule schedule : schedules) {
                scheduleDTOS.add(convertSchedule(schedule));
            }
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting schedules for customer", exception);
        }
        return scheduleDTOS;
    }

    private ScheduleDTO convertSchedule(Schedule schedule) {
        ScheduleDTO scheduleDTO = new ScheduleDTO();
        scheduleDTO.setId(schedule.getId());
        scheduleDTO.setDate(schedule.getDate());

        List<Long> petIds = new ArrayList<>();
        List<Pet> pets = schedule.getPets();
        if (pets!=null) {
            for (Pet pet : pets) {
                petIds.add(pet.getId());}
        }
        scheduleDTO.setPetIds(petIds);

        List<Long> employeeIds = new ArrayList<>();
        List<Employee> employees = schedule.getEmployees();
        if (employees!=null) {
            for (Employee employee : employees) {
                employeeIds.add(employee.getId());}
        }
        scheduleDTO.setEmployeeIds(employeeIds);

        scheduleDTO.setActivities(schedule.getActivities());

        return  scheduleDTO;
    }

}
