package com.udacity.jdnd.course3.critter.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    private PetService petService;

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO)  {
        PetDTO petDTOResult;
        try {
            Pet pet = petService.savePet(new Pet(petDTO.getType(), petDTO.getName(), petDTO.getBirthDate(), petDTO.getNotes()), petDTO.getOwnerId());
            petDTOResult = convertPet(pet);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error saving pet", exception);
        }
        return petDTOResult;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {
        PetDTO result;
        try {
        Pet pet = petService.getPetById(petId);
        result = convertPet(pet);
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting pet", exception);
        }
        return result;
    }

    @GetMapping
    public List<PetDTO> getPets(){
        List<PetDTO> result = new ArrayList<>();
           try {
        List<Pet> pets = petService.getPets();
        System.out.println("list of all pets:" + result == null?"there's at least one pet":"null");
        for (Pet pet : pets) {
            result.add(convertPet(pet));
        }
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting all pets", exception);
        }
        return result;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {
        List<PetDTO> result = new ArrayList<>();
           try {
        List<Pet> pets = petService.getPetsByCustomerId(ownerId);
        for (Pet pet : pets) {
            result.add(convertPet(pet));
        }
        } catch (Exception exception){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error getting pets by owner", exception);
        }
        return result;
    }

    private PetDTO convertPet (Pet pet){
        PetDTO petDTO = new PetDTO();
        petDTO.setId(pet.getId());
        petDTO.setBirthDate(pet.getBirthDate());
        petDTO.setName(pet.getName());
        petDTO.setNotes(pet.getNotes());
        petDTO.setType(pet.getType());
        petDTO.setOwnerId(pet.getCustomer().getId());
        return petDTO;
    }
}
