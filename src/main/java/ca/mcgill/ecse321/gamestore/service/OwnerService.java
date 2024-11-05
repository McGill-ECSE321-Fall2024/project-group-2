package ca.mcgill.ecse321.gamestore.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse321.gamestore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Owner;
import jakarta.transaction.Transactional;

@Service
public class OwnerService {

    // Repositories to interact with the database
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    // Retrieve all owners
    @Transactional
    public Iterable<Owner> findAllOwner() {
        return ownerRepository.findAll();
    }

    // Retrieve a specific owner by email
    @Transactional
    public Owner getOwner(String email) {
        Owner owner = ownerRepository.findOwnerByEmail(email);

        // Check if the owner is found
        if (owner == null) {
            throw new IllegalArgumentException("Manager Not Found");
        }
        // Check if email is empty
        else if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("The email cannot be empty!");
        } else {
            return owner; // Return the found owner
        }
    }

    // Create a new owner
    @Transactional
    public Owner createOwner(String userID, String name, String email, String password) {
        // Check if all inputs are null
        if (name == null && userID == null && email == null && password == null) {
            throw new IllegalArgumentException("All inputs null!");
        }
        // Check if all fields are empty
        else if (name.trim().isEmpty() && userID.trim().isEmpty() && email.trim().isEmpty() && password.trim().isEmpty()) {
            throw new IllegalArgumentException("All fields are empty!");
        }
        // Validate individual fields
        else if (userID == null || userID.trim().isEmpty()) {
            throw new IllegalArgumentException("The userID cannot be empty!");
        }
        else if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The name cannot be empty!");
        }
        else if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("The email cannot be empty!");
        }
        else if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("The password cannot be empty!");
        }
        // Check if user already exists
        else if (personRepository.findPersonByEmail(email) != null
                || ownerRepository.findOwnerByEmail(email) != null
                || customerRepository.findCustomerByEmail(email) != null
                || employeeRepository.findEmployeeByEmail(email) != null
                || accountRepository.findAccountByEmail(email) != null) {
            throw new IllegalArgumentException("User with that email already exists!");
        } else {
            // Validate email format
            String emailTrimmed = email.trim();
            Pattern pattern = Pattern.compile("^(\\S+)@(\\S+)\\.((com)|(ca))$");
            Matcher matcher = pattern.matcher(emailTrimmed);
            if (!matcher.find()) {
                throw new IllegalArgumentException("The email is invalid!");
            } else {
                // Create and save the owner
                Owner owner = new Owner(userID, name, email, password);
                ownerRepository.save(owner);
                return owner; // Return the newly created owner
            }
        }
    }

    // Update owner's password
    @Transactional
    public Owner updateOwnerPassword(String email, String oldPassword, String newPassword) {
        Owner owner = ownerRepository.findOwnerByEmail(email);

        // Check if the user exists
        if (owner == null) {
            throw new IllegalArgumentException("User Not Found");
        }
        // Validate new password
        else if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("The password cannot be empty!");
        } else {
            // Verify the old password
            if (owner.getPassword().equals(oldPassword)) {
                owner.setPassword(newPassword); // Update password
                return owner; // Return the updated owner
            } else {
                throw new IllegalArgumentException("Incorrect old password!");
            }
        }
    }
}

