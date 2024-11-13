package ca.mcgill.ecse321.gamestore.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.repository.*;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

    // Inject repositories to interact with the database
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

    // Inject PasswordEncoder to hash passwords
    @Autowired
    private PasswordEncoder passwordEncoder;

    // Retrieve an employee by email
    @Transactional
    public Employee getEmployee(String email) {
        // Validate email input
        if (email == null || email.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The email cannot be empty!");
        }

        // Find employee by email
        Employee employee = employeeRepository.findEmployeeByEmail(email.trim());
        if (employee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Employee Not Found");
        }

        return employee; // Return the found employee
    }

    // Retrieve all employees
    @Transactional
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    // Create a new employee
    @Transactional
    public Employee createEmployee(String userID, String name, String email, String password) {
        // Validate each input field
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

        // Check if email already exists in the system
        if (personRepository.findPersonByEmail(email.trim()) != null
                || customerRepository.findCustomerByEmail(email.trim()) != null
                || employeeRepository.findEmployeeByEmail(email.trim()) != null
                || ownerRepository.findOwnerByEmail(email.trim()) != null
                || accountRepository.findAccountByEmail(email.trim()) != null) {
            throw new GameStoreException(HttpStatus.CONFLICT, "User with that email already exists!");
        }

        // Validate email format using regular expression
        Pattern pattern = Pattern.compile("^(\\S+)@(\\S+)\\.((com)|(ca))$");
        Matcher matcher = pattern.matcher(email.trim());
        if (!matcher.matches()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The email is invalid!");
        }

        // Hash the password before saving to the database
        String hashedPassword = passwordEncoder.encode(password);

        // Create and save the new employee
        Employee employee = new Employee(userID, name, email.trim(), hashedPassword);
        employeeRepository.save(employee);
        return employee; // Return the newly created employee
    }

    // Update an employee's password
    @Transactional
    public Employee updateEmployeePassword(String email, String oldPassword, String newPassword) {
        // Find the employee by email
        Employee employee = employeeRepository.findEmployeeByEmail(email.trim());

        // Check if employee exists
        if (employee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Employee not found");
        }
        // Validate the new password
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The new password cannot be empty!");
        }

        // Verify the old password with the hashed password
        if (!passwordEncoder.matches(oldPassword, employee.getPassword())) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Incorrect old password!");
        }

        // Hash the new password and update it
        String hashedNewPassword = passwordEncoder.encode(newPassword);
        employee.setPassword(hashedNewPassword);
        return employeeRepository.save(employee); // Save the employee with the updated password
    }

    // Delete an employee by email
    @Transactional
    public boolean deleteEmployee(String email) {
        // Find the employee by email
        Employee employee = employeeRepository.findEmployeeByEmail(email.trim());

        // Check if the employee exists
        if (employee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Employee with that email does not exist!");
        }

        // Delete the employee
        employeeRepository.delete(employee);
        return true; // Return true upon successful deletion
    }
}

