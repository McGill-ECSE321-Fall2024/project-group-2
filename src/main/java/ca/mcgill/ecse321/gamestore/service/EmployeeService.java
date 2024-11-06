package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional
    public List<Employee> getAllEmployees() {
        return (List<Employee>) employeeRepository.findAll();
    }

    @Transactional
    public Employee getEmployeeByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty.");
        }
        Employee employee = employeeRepository.findEmployeeByEmail(email.trim());
        if (employee == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "No employee found with email: " + email);
        }
        return employee;
    }

    @Transactional
    public Employee createEmployee(String userID, String name, String email, String password) {
        validateEmployeeInputs(name, email, password);

        if (personRepository.findPersonByEmail(email.trim()) != null
                || employeeRepository.findEmployeeByEmail(email.trim()) != null
                || customerRepository.findCustomerByEmail(email.trim()) != null
                || ownerRepository.findOwnerByEmail(email.trim()) != null
                || accountRepository.findAccountByEmail(email.trim()) != null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST,
                    "An employee or user with this email already exists.");
        }

        Employee employee = new Employee(userID, name, email.trim(), password);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployee(String email, String userID, String newName, String newPassword) {
        validateEmployeeInputs(newName, email, newPassword);
        Employee employee = getEmployeeByEmail(email.trim());

        employee.setUserID(userID.trim());
        employee.setName(newName.trim());
        employee.setPassword(newPassword);
        return employeeRepository.save(employee);
    }

    @Transactional
    public void deleteEmployee(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty.");
        }
        Employee employee = getEmployeeByEmail(email.trim());
        employeeRepository.delete(employee);
    }

    private void validateEmployeeInputs(String name, String email, String password) {
        if (name == null || name.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Employee name cannot be empty.");
        }
        if (email == null || email.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Email cannot be null or empty.");
        }

        // Validate email format if not null or empty
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        Matcher matcher = emailPattern.matcher(email.trim());
        if (!matcher.matches()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Invalid email format.");
        }

        if (password == null || password.trim().isEmpty() || password.length() < 8) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters long.");
        }
    }

}




