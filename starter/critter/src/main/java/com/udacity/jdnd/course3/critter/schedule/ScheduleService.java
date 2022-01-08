package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetRepository;
import com.udacity.jdnd.course3.critter.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CustomerRepository customerRepository;



    public Schedule saveSchedule(Schedule schedule, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> employees = employeeRepository.findAllById(employeeIds);
        schedule.setEmployees(employees);

        List<Pet> pets = petRepository.findAllById(petIds);
        schedule.setPets(pets);

        return scheduleRepository.save(schedule);
    }

    public List<Schedule> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules;
    }

    public List<Schedule> getScheduleForPet(long petId) {
        Optional<Pet> pet = petRepository.findById(petId);
        if(!pet.isPresent()){
            return null;
        }
        List<Schedule> schedules;
        schedules = scheduleRepository.findByPets(pet.get());
        return  schedules;
    }

    public List<Schedule> getScheduleForEmployee(long employeeId) {
        Optional<Employee> employee = employeeRepository.findById(employeeId);
        if(!employee.isPresent()){
            return null;
        }
        List<Schedule> schedules = scheduleRepository.findByEmployees(employee.get());
        return  schedules;
    }

    public List<Schedule> getScheduleForCustomer(long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(!customer.isPresent()){
            return null; }

        List<Schedule> schedules = scheduleRepository.findByPetsIn(customer.get().getPets());
        return  schedules;
    }
}
