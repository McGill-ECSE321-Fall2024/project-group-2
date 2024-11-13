package ca.mcgill.ecse321.gamestore.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.repository.*;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private PasswordEncoder passwordEncoder; // Inject PasswordEncoder for password encoding

    /**
     * Retrieves a customer based on the provided email.
     * @param email the email of the customer to retrieve
     * @return the customer with the specified email
     * @throws GameStoreException if email is null/empty or customer not found
     */
    @Transactional
    public Customer getCustomer(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The email cannot be empty!");
        }

        Customer customer = customerRepository.findCustomerByEmail(email.trim());
        if (customer == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Customer Not Found");
        }

        return customer;
    }

    /**
     * Retrieves all customers.
     * @return a list of all customers
     */
    @Transactional
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    /**
     * Creates a new customer with the specified details.
     * @param userID the unique ID for the customer
     * @param name the name of the customer
     * @param email the email of the customer
     * @param password the password for the customer account
     * @return the created customer
     * @throws GameStoreException if any input is invalid or email is already in use
     */
    @Transactional
    public Customer createCustomer(String userID, String name, String email, String password) {
        // Validate non-empty input fields
        if (userID == null || userID.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The user ID cannot be empty!");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The name cannot be empty!");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The email cannot be empty!");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The password cannot be empty!");
        }

        // Check if email is already associated with another user
        if (personRepository.findPersonByEmail(email.trim()) != null
                || customerRepository.findCustomerByEmail(email.trim()) != null
                || employeeRepository.findEmployeeByEmail(email.trim()) != null
                || ownerRepository.findOwnerByEmail(email.trim()) != null
                || accountRepository.findAccountByEmail(email.trim()) != null) {
            throw new GameStoreException(HttpStatus.CONFLICT, "User with that email already exists!");
        }

        // Validate email format using regex pattern
        Pattern pattern = Pattern.compile("^(\\S+)@(\\S+)\\.((com)|(ca))$");
        Matcher matcher = pattern.matcher(email.trim());
        if (!matcher.matches()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The email is invalid!");
        }

        // Encode the password before saving the customer
        String encodedPassword = passwordEncoder.encode(password);

        // Create and save the new customer
        Customer customer = new Customer(userID, name, email.trim(), encodedPassword);
        customerRepository.save(customer);
        return customer;
    }

    /**
     * Updates the password for an existing customer.
     * @param email the email of the customer
     * @param oldPassword the current password for validation
     * @param newPassword the new password to set
     * @return the updated customer with the new password
     * @throws GameStoreException if customer not found, old password incorrect, or new password invalid
     */
    @Transactional
    public Customer updateCustomerPassword(String email, String oldPassword, String newPassword) {
        Customer customer = customerRepository.findCustomerByEmail(email.trim());

        if (customer == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The new password cannot be empty!");
        }

        // Check if old password matches the stored password using PasswordEncoder
        if (!passwordEncoder.matches(oldPassword, customer.getPassword())) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Incorrect old password!");
        }

        // Encode the new password and update the customer record
        String encodedNewPassword = passwordEncoder.encode(newPassword);
        customer.setPassword(encodedNewPassword);
        return customerRepository.save(customer);
    }

    /**
     * Deletes a customer based on the provided email.
     * @param email the email of the customer to delete
     * @return true if deletion is successful
     * @throws GameStoreException if customer not found
     */
    @Transactional
    public boolean deleteCustomer(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email.trim());

        if (customer == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Customer with that email does not exist!");
        }

        // Delete the customer
        customerRepository.delete(customer);
        return true;
    }
}
