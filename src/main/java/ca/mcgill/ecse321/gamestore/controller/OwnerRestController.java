package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.gamestore.dto.OwnerDto;
import ca.mcgill.ecse321.gamestore.dto.OwnerListDto;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnerRestController {

    // Injecting the OwnerService to handle business logic
    @Autowired
    private OwnerService service;

    // Endpoint to retrieve all owners
    @GetMapping("/owner")
    public OwnerListDto findAllOwner() {
        // Create a list to hold OwnerDto objects
        List<OwnerDto> owner = new ArrayList<OwnerDto>();
        // Loop through all Owner models and convert them to OwnerDto
        for (Owner model : service.findAllOwner()) {
            owner.add(new OwnerDto(model));
        }
        // Return the list of OwnerDto wrapped in OwnerListDto
        return new OwnerListDto(owner);
    }

    // Endpoint to retrieve a specific owner by email
    @GetMapping(value = { "/owners/{email}", "/owners/{email}/" })
    public OwnerDto getOwner(@PathVariable("email") String email) {
        // Fetch the Owner model using the email
        Owner owner = service.getOwner(email);
        // Convert the Owner model to OwnerDto
        OwnerDto ownerBody = new OwnerDto(owner);
        // Return the OwnerDto
        return ownerBody;
    }

    // Endpoint to create a new owner
    @PostMapping(value = { "/owners/create", "/owners/create/" })
    public OwnerDto createOwner(@RequestBody OwnerDto ownerDto) {
        // Create a new Owner using the provided data from OwnerDto
        Owner owner = service.createOwner(
                ownerDto.getUserID(),
                ownerDto.getName(),
                ownerDto.getEmail(),
                ownerDto.getPassword());
        // Convert the newly created Owner model to OwnerDto
        OwnerDto ownerBody = new OwnerDto(owner);
        // Return the OwnerDto
        return ownerBody;
    }

    // Endpoint to update an owner's password
    @PutMapping(value = { "/owners/update/{newPassword}", "/owners/update/{newPassword}/" })
    public OwnerDto updateOwner(@RequestBody OwnerDto ownerDto, @PathVariable("newPassword") String newPassword) {
        // Update the owner's password and fetch the updated Owner model
        Owner owner = service.updateOwnerPassword(ownerDto.getEmail(), ownerDto.getPassword(), newPassword);
        // Convert the updated Owner model to OwnerDto
        OwnerDto ownerBody = new OwnerDto(owner);
        // Return the updated OwnerDto
        return ownerBody;
    }

}
