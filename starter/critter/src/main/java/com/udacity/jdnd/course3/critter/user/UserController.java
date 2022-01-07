package com.udacity.jdnd.course3.critter.user;
import com.udacity.jdnd.course3.critter.pet.Pet;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        CustomerDTO customerDTOResult;
        //try {
        Customer customer = customerService.saveCustomer(new Customer(customerDTO.getName(),customerDTO.getPhoneNumber(),customerDTO.getNotes()));
        customerDTOResult = convertCustomer(customer);
//        } catch (Exception exception){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving customer", exception);
//        }
        return customerDTOResult;
    }



    @GetMapping("/customer")
    public List<CustomerDTO> getAllCustomers(){
        List<CustomerDTO> customerDTOList = new ArrayList<>();
        //   try {
        List<Customer> customers = customerService.getCustomers();
        for (Customer customer : customers) {
            customerDTOList.add(convertCustomer(customer));
        }
//        } catch (Exception exception){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error retrieving all customer", exception);
//        }
        return customerDTOList;
    }

    @GetMapping("/customer/pet/{petId}")
    public CustomerDTO getOwnerByPet(@PathVariable long petId){
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/employee/{employeeId}")
    public EmployeeDTO getEmployee(@PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @PutMapping("/employee/{employeeId}")
    public void setAvailability(@RequestBody Set<DayOfWeek> daysAvailable, @PathVariable long employeeId) {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeRequestDTO employeeDTO) {
        throw new UnsupportedOperationException();
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
                petIds.add(pet.getId());
            }
        }
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

}
