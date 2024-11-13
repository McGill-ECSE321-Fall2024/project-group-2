package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.gamestore.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gamestore.dto.OwnerDto;
import ca.mcgill.ecse321.gamestore.dto.OwnerListDto;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping(value = { "/owner/{email}", "/owner/{email}/" })
    public OwnerDto getOwner(@PathVariable("email") String email) {
        // Fetch the Owner model using the email
        Owner owner = service.getOwner(email);
        // Convert the Owner model to OwnerDto
        OwnerDto ownerBody = new OwnerDto(owner);
        // Return the OwnerDto
        return ownerBody;
    }

    // Endpoint to create a new owner
    @PostMapping(value = { "/owner/", "/owner" })
    @ResponseStatus(HttpStatus.CREATED)
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
    @PutMapping(value= {"/owner/{email}/","/owner/{email}"})
    public OwnerDto updateCustomerPassword(
            @PathVariable String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        // Updates customer password and returns the updated customer as a DTO
        Owner updatedOwner = service.updateOwnerPassword(
                email, oldPassword, newPassword
        );
        return new OwnerDto(updatedOwner);
    }
    @DeleteMapping("/owner/{email}")
    public String deleteOwner(@PathVariable String email) {
        // Calls service to delete customer by email
        service.deleteOwner(email);
        // Returns a confirmation message upon successful deletion
        return "Owner with email " + email + " deleted successfully.";
    }

}
