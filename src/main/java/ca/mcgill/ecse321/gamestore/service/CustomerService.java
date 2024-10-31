package ca.mcgill.ecse321.gamestore.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.repository.*;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for managing Customer operations.
 */
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository CustomerRepository;
    @Autowired
    private AccountRepository AccountRepository;
    @Autowired
    private PersonRepository PersonRepository;
    @Autowired
    private EmployeeRepository EmployeeRepository;
    @Autowired
    private OwnerRepository OwnerRepository;

    /**
     * Retrieve a customer by email.
     * @param email Customer's email.
     * @return The found customer.
     * @throws IllegalArgumentException if validation fails or customer is not found.
     */
    @Transactional
    public Customer getCustomer(String email) {
        // Validate email
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("The email cannot be empty!");
        }

        Customer customer = CustomerRepository.findCustomerByEmail(email.trim());
        if (customer == null) {
            throw new IllegalArgumentException("Customer Not Found");
        }

        return customer;
    }

    /**
     * Retrieve all customers.
     * @return List of all customers.
     */
    @Transactional
    public List<Customer> getAllCustomers() {
        return (List<Customer>) CustomerRepository.findAll();
    }

    /**
     * Create a new customer.
     * @param userID Customer's user ID.
     * @param name Customer's name.
     * @param email Customer's email.
     * @param password Customer's password.
     * @return The created customer.
     * @throws IllegalArgumentException if validation fails or email already exists.
     */
    @Transactional
    public Customer createCustomer(String userID, String name, String email, String password) {
        // Validate inputs
        if (userID == null || userID.trim().isEmpty()) {
            throw new IllegalArgumentException("The user ID cannot be empty!");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("The name cannot be empty!");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("The email cannot be empty!");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new IllegalArgumentException("The password cannot be empty!");
        }

        // Check if the email is already associated with another user
        if (PersonRepository.findPersonByEmail(email.trim()) != null
                || CustomerRepository.findCustomerByEmail(email.trim()) != null
                || EmployeeRepository.findEmployeeByEmail(email.trim()) != null
                || OwnerRepository.findOwnerByEmail(email.trim()) != null
                || AccountRepository.findAccountByEmail(email.trim()) != null) {
            throw new IllegalArgumentException("User with that email already exists!");
        }

        // Validate email format
        String emailTrimmed = email.trim();
        Pattern pattern = Pattern.compile("^(\\S+)@(\\S+)\\.((com)|(ca))$");
        Matcher matcher = pattern.matcher(emailTrimmed);
        if (!matcher.find()) {
            throw new IllegalArgumentException("The email is invalid!");
        }

        // Create and save the customer
        Customer customer = new Customer("false", userID, name, emailTrimmed, password);
        CustomerRepository.save(customer);
        return customer;
    }

    /**
     * Update a customer's password.
     * @param email Customer's email.
     * @param oldPassword Customer's old password.
     * @param newPassword Customer's new password.
     * @return The updated customer.
     * @throws IllegalArgumentException if validation fails or old password is incorrect.
     */
    @Transactional
    public Customer updateCustomerPassword(String email, String oldPassword, String newPassword) {
        Customer customer = CustomerRepository.findCustomerByEmail(email.trim());

        if (customer == null) {
            throw new IllegalArgumentException("Customer Not Found");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("The new password cannot be empty!");
        }

        if (!customer.getPassword().equals(oldPassword)) {
            throw new IllegalArgumentException("Incorrect old password!");
        }

        customer.setPassword(newPassword);
        return CustomerRepository.save(customer);
    }

    /**
     * Delete a customer by email.
     * @param email Customer's email.
     * @return true if deletion is successful.
     * @throws IllegalArgumentException if the customer is not found.
     */
    @Transactional
    public boolean deleteCustomer(String email) {
        Customer customer = CustomerRepository.findCustomerByEmail(email.trim());

        if (customer == null) {
            throw new IllegalArgumentException("Customer with that email does not exist!");
        }

        CustomerRepository.delete(customer);
        return true;
    }
}

