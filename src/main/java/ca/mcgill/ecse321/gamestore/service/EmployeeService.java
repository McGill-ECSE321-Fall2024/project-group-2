package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Service class for managing Employee operations.
 */
@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository EmployeeRepository;

    /**
     * Retrieve all employees.
     * @return List of all employees.
     */
    @Transactional
    public List<Employee> getAllEmployees() {
        return (List<Employee>) EmployeeRepository.findAll();
    }

    /**
     * Retrieve an employee by email.
     * @param email Employee's email.
     * @return The found employee.
     * @throws ResponseStatusException if the employee is not found.
     */
    @Transactional
    public Employee getEmployeeByEmail(String email) {
        String emailTrimmed = email.trim();
        Employee employee = EmployeeRepository.findEmployeeByEmail(emailTrimmed);
        if (employee == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No employee found with email: " + email);
        }
        return employee;
    }

    /**
     * Create a new employee.
     * @param name Employee's name.
     * @param email Employee's email.
     * @param password Employee's password.
     * @return The created employee.
     * @throws ResponseStatusException if validation fails or email already exists.
     */
    @Transactional
    public Employee createEmployee(String name, String email, String password) {
        validateEmployeeInputs(name, email, password);

        if (EmployeeRepository.findEmployeeByEmail(email.trim()) != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "An employee with this email already exists.");
        }

        Employee employee = new Employee("false", "123", name, email.trim(), password);
        return EmployeeRepository.save(employee);
    }

    /**
     * Update an existing employee's details.
     * @param email Employee's email.
     * @param newName New name for the employee.
     * @param newPassword New password for the employee.
     * @return The updated employee.
     * @throws ResponseStatusException if the employee is not found or validation fails.
     */
    @Transactional
    public Employee updateEmployee(String email, String newName, String newPassword) {
        validateEmployeeInputs(newName, email, newPassword);

        Employee employee = getEmployeeByEmail(email.trim());
        employee.setName(newName.trim());
        employee.setPassword(newPassword);
        return EmployeeRepository.save(employee);
    }

    /**
     * Delete an employee by email.
     * @param email Employee's email.
     * @throws ResponseStatusException if the employee is not found.
     */
    @Transactional
    public void deleteEmployee(String email) {
        Employee employee = getEmployeeByEmail(email.trim());
        EmployeeRepository.delete(employee);
    }

    /**
     * Validate employee input fields.
     * @param name Employee's name.
     * @param email Employee's email.
     * @param password Employee's password.
     * @throws ResponseStatusException if validation fails.
     */
    private void validateEmployeeInputs(String name, String email, String password) {
        // Validate name
        if (name == null || name.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Employee name cannot be empty.");
        }

        // Validate email format
        String emailTrimmed = email.trim();
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = emailPattern.matcher(emailTrimmed);
        if (!matcher.matches()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Invalid email format.");
        }

        // Validate password length
        if (password == null || password.length() < 8) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Password must be at least 8 characters long.");
        }
    }
}

