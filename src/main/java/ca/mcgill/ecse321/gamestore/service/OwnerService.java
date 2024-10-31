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


    @Transactional
    public Owner getOwner(String email) {
        Owner owner = ownerRepository.findOwnerByEmail(email);
        // Check if manager not found
        if (owner == null) {
            throw new IllegalArgumentException( "Manager Not Found");
        } // Check if email is empty
        else if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException( "The email cannot be empty!");
        } else {
            return owner;
        }

    }

    @Transactional
    public Owner createOwner(String userID, String name, String email, String password) {
        if (name == null  && userID ==null && email == null && password == null) {
            throw new IllegalArgumentException( "All inputs null!");
        }
        // Check is all inputs are inputes
        else if (name.trim().isEmpty() && userID.trim().isEmpty() && email.trim().isEmpty()
                && password.trim().isEmpty()) {
            throw new IllegalArgumentException( "All fields are empty!");
        }
        else if (userID == null || userID.trim().isEmpty()) {
            throw new IllegalArgumentException("The  userID cannot be empty!");
        }
        else if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The  name cannot be empty!");
        }
        // Check if email is empty
        else if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException( "The email cannot be empty!");
        }
        // Check if password is empty
        else if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException( "The password cannot be empty!");
        }
        // Check if user already exists
        else if (personRepository.findPersonByEmail(email) != null
                || ownerRepository.findOwnerByEmail(email) != null
                || customerRepository.findCustomerByEmail(email) != null
                || employeeRepository.findEmployeeByEmail(email) != null
                || accountRepository.findAccountByEmail(email) != null) {
            throw new IllegalArgumentException( "User with that email already exists!");
        } else {
            String emailTrimmed = email.trim();
            Pattern pattern = Pattern.compile("^(\\S+)@(\\S+)\\.((com)|(ca))$");
            Matcher matcher = pattern.matcher(emailTrimmed);
            // Check if email is valid format
            if (!matcher.find()) {
                throw new IllegalArgumentException("The email is invalid!");
            } else {
                Owner owner = new Owner(name, userID, email, password);
                ownerRepository.save(owner);
                return owner;
            }
        }
    }
    @Transactional
    public Owner updateOwnerPassword(String email, String oldPassword, String newPassword) {
        Owner owner = ownerRepository.findOwnerByEmail(email);
        // Check if user exists
        if (owner == null) {
            throw new IllegalArgumentException( "User Not Found");
        } // Check if password is empty
        else if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException( "The password cannot be empty!");
        } else {
            if (owner.getPassword().equals(oldPassword)) {
                owner.setPassword(newPassword);
                return owner;
            } else {
                throw new IllegalArgumentException("Incorrect old password!");
            }
        }
    }
    @Transactional
    public boolean deleteOwner(String email) {
        Owner owner = ownerRepository.findOwnerByEmail(email);
        try {
            // Check if user exists
            if (ownerRepository.findOwnerByEmail(email) == null) {
                throw new IllegalArgumentException( "User with that email does not exist!");
            } else {
                // Delete manager
                ownerRepository.delete(owner);
                return true;
            }
        } catch (Exception e) {
            return false;
        }

    }
}