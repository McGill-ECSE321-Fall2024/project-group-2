package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.OwnerDto;
import ca.mcgill.ecse321.gamestore.dto.OwnerListDto;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * REST Controller for Owner-related operations.
 */

@RestController
public class OwnerRestController {

    @Autowired
    private OwnerService service;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get all owners.
     *
     * @return OwnerListDto containing a list of OwnerDto.
     */
    @GetMapping("/owner")
    @ResponseStatus(HttpStatus.OK) // Returns 200 OK when successful
    public OwnerListDto findAllOwner() {
        List<OwnerDto> owners = new ArrayList<>();
        for (Owner model : service.findAllOwner()) {
            owners.add(new OwnerDto(model));
        }
        return new OwnerListDto(owners);
    }

    /**
     * Get an owner by email.
     *
     * @param email Owner's email.
     * @return OwnerDto.
     */
    @GetMapping(value = { "/owner/{email}", "/owner/{email}/" })
    @ResponseStatus(HttpStatus.OK) // Returns 200 OK when the owner is found
    public OwnerDto getOwner(@PathVariable("email") String email) {
        Owner owner = service.getOwner(email);
        if (owner == null) {
            throw new IllegalArgumentException("Owner not found.");
        }
        return new OwnerDto(owner);
    }

    /**
     * Create a new owner.
     *
     * @param ownerDto OwnerDto containing the owner details.
     * @return OwnerDto of the created owner.
     */
    @PostMapping(value = { "/owner/", "/owner" })
    @ResponseStatus(HttpStatus.CREATED) // Returns 201 Created when successful
    public OwnerDto createOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = service.createOwner(
                ownerDto.getUserID(),
                ownerDto.getName(),
                ownerDto.getEmail(),
                ownerDto.getPassword());
        return new OwnerDto(owner);
    }

    /**
     * Update an owner's password.
     *
     * @param email       Owner's email.
     * @param oldPassword Owner's old password.
     * @param newPassword Owner's new password.
     * @return OwnerDto of the updated owner.
     */
    @PutMapping(value = { "/owner/{email}/", "/owner/{email}" })
    @ResponseStatus(HttpStatus.OK) // Returns 200 OK when the password is updated
    public OwnerDto updateOwnerPassword(
            @PathVariable String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        Owner updatedOwner = service.updateOwnerPassword(
                email, oldPassword, newPassword
        );
        return new OwnerDto(updatedOwner);
    }

    /**
     * Delete an owner by email.
     *
     * @param email Owner's email.
     * @return Success message as String.
     */
    @DeleteMapping("/owner/{email}")
    @ResponseStatus(HttpStatus.OK) // Returns 200 OK when the owner is deleted
    public String deleteOwner(@PathVariable String email) {
        service.deleteOwner(email);
        return "Owner with email " + email + " deleted successfully.";
    }

    /**
     * Owner login endpoint.
     *
     * @param email    Owner's email.
     * @param password Owner's password.
     * @return OwnerDto of the logged-in owner.
     */
    @PostMapping("/owner/login")
    @ResponseStatus(HttpStatus.OK) // Returns 200 OK when login is successful
    public OwnerDto ownerLogin(@RequestParam String email, @RequestParam String password) {
        Owner owner = service.getOwner(email);

        // Check if the owner exists
        if (owner == null) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // Validate the password
        if (!passwordEncoder.matches(password, owner.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        return new OwnerDto(owner);
    }
}

