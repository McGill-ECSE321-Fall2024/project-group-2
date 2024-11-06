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


    @Transactional
    public List<Customer> getAllCustomers() {
        return (List<Customer>) customerRepository.findAll();
    }

    @Transactional
    public Customer createCustomer(String userID, String name, String email, String password) {
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

        // Check if the email is already associated with another user
        if (personRepository.findPersonByEmail(email.trim()) != null
                || customerRepository.findCustomerByEmail(email.trim()) != null
                || employeeRepository.findEmployeeByEmail(email.trim()) != null
                || ownerRepository.findOwnerByEmail(email.trim()) != null
                || accountRepository.findAccountByEmail(email.trim()) != null) {
            throw new GameStoreException(HttpStatus.CONFLICT, "User with that email already exists!");
        }

        // Validate email format
        Pattern pattern = Pattern.compile("^(\\S+)@(\\S+)\\.((com)|(ca))$");
        Matcher matcher = pattern.matcher(email.trim());
        if (!matcher.matches()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The email is invalid!");
        }

        Customer customer = new Customer(userID, name, email.trim(), password);
        customerRepository.save(customer);
        return customer;
    }


    @Transactional
    public Customer updateCustomerPassword(String email, String oldPassword, String newPassword) {
        Customer customer = customerRepository.findCustomerByEmail(email.trim());

        if (customer == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Customer not found");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The new password cannot be empty!");
        }

        if (!customer.getPassword().equals(oldPassword)) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Incorrect old password!");
        }

        customer.setPassword(newPassword);
        return customerRepository.save(customer);
    }

    @Transactional
    public boolean deleteCustomer(String email) {
        Customer customer = customerRepository.findCustomerByEmail(email.trim());

        if (customer == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Customer with that email does not exist!");
        }

        customerRepository.delete(customer);
        return true;
    }
}


