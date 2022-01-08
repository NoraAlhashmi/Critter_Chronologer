package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.user.Customer;
import com.udacity.jdnd.course3.critter.user.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public Pet savePet(Pet pet, long ownerId) {
        Customer customer = customerRepository.getOne(ownerId);
        System.out.println("customer ID= "+customer.getId());
        pet.setCustomer(customer);
        pet = petRepository.save(pet);
        customer.insertPet(pet);
        customerRepository.save(customer);
        return pet;
    }

    public  Pet getPetById (Long petId){
        Optional<Pet> pet = petRepository.findById(petId);
        if (pet.isPresent()){
            return (Pet) pet.get();
        }
        return null;
    }

    public List<Pet> getPetsByCustomerId(long customerId) {
        List<Pet> pets = petRepository.findPetByCustomerId(customerId);
        return pets;
    }


    public List<Pet> getPets() {
        List<Pet> pets = petRepository.findAll();
        return pets;
    }


}
