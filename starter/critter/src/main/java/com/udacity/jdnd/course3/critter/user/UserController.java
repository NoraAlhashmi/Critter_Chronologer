package com.udacity.jdnd.course3.critter.user;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Handles web requests related to Users.
 *
 * Includes requests for both customers and employees. Splitting this into separate user and customer controllers
 * would be fine too, though that is not part of the required scope for this class.
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO customerDTOResult;
        try {
        Customer customer = customerService.saveCustomer(new Customer(customerDTO.getName(),customerDTO.getPhoneNumber(),customerDTO.getNotes()));
        customerDTOResult = convertCustomer(customer);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving customer", exception);
        }
        return customerDTOResult;
    }



    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
           try {
        List<Customer> customers = customerService.getCustomers();
        for (Customer customer : customers) {
            customerDTOList.add(convertCustomer(customer));
        }
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error retrieving all customer", exception);
        }
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        CustomerDTO customerDTOResult;
        try {
        Customer customer = customerService.getCustomerByPet(petId);
        customerDTOResult = convertCustomer(customer);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving customer", exception);
        }
        return customerDTOResult;
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        EmployeeDTO employeeDTOResult;
        try {
            Employee employee = employeeService.saveEmployee(new Employee(employeeDTO.getName(),employeeDTO.getSkills(),employeeDTO.getDaysAvailable()));
            employeeDTOResult = convertEmployee(employee);

        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving employee", exception);
        }
        return employeeDTOResult;
    }



    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        EmployeeDTO employeeDTO;
        try {
            Employee employee = employeeService.getEmployeeById(employeeId);
            employeeDTO = convertEmployee(employee);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting employee", exception);
        }
        return employeeDTO;
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        try {
            employeeService.setAvailability(daysAvailable,employeeId);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving employee availability", exception);
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        List<EmployeeDTO> employeeDTOS= new ArrayList<>();
        try {
            List<Employee> employees = employeeService.findEmployeesForService(employeeDTO.getDate(),employeeDTO.getSkills());
            for (Employee employee : employees){
                employeeDTOS.add(convertEmployee(employee));
            }
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error retrieving available employee", exception);
        }
        return employeeDTOS;
    }

    private CustomerDTO convertCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setNotes(customer.getNotes());
        List<Pet> pets = customer.getPets();
        List<Long> petIds = new ArrayList<>();
        if (pets!=null) {
            for (Pet pet : pets) {
                petIds.add(pet.getId());}
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    private EmployeeDTO convertEmployee(Employee employee) {
        System.out.println("convertEmployee");

        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDaysAvailable(employee.getDaysAvailable());
        System.out.println("convertedEmployee name = "+employeeDTO.getName());

        return employeeDTO;
    }

}
