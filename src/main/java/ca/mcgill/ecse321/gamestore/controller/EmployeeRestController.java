package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.*;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Employee-related operations.
 */
@CrossOrigin(origins = "*")
@RestController
@CrossOrigin(origins = "http://localhost:5173")  // Allow requests from Vue.js
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Get all employees.
     *
     * @return EmployeeListDto containing a list of EmployeeResponseDto.
     */
    @GetMapping("/employee")
    public EmployeeListDto findAllEmployee() {
        List<EmployeeResponseDto> employee = new ArrayList<EmployeeResponseDto>();
        for (Employee model : employeeService.getAllEmployees()) {
            employee.add(new EmployeeResponseDto(model));
        }
        return new EmployeeListDto(employee);
    }

    /**
     * Get an employee by email.
     *
     * @param email Employee's email.
     * @return EmployeeResponseDto.
     */
    @GetMapping("/employee/{email}")
    public EmployeeResponseDto getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.getEmployee(email);
        return new EmployeeResponseDto(employee);
    }

    /**
     * Create a new employee.
     *
     * @param employeeRequestDto EmployeeRequestDto containing the employee details.
     * @return EmployeeResponseDto of the created employee.
     */
    @PostMapping("/employee")
    @ResponseStatus(HttpStatus.CREATED)
    public EmployeeResponseDto createEmployee(@RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeService.createEmployee(
                employeeRequestDto.getUserID(),
                employeeRequestDto.getName(),
                employeeRequestDto.getEmail(),
                employeeRequestDto.getPassword()
        );
        return new EmployeeResponseDto(employee);
    }


    @PutMapping(value= {"/employee/{email}/","/owner/{email}"})
    public EmployeeResponseDto updateEmployeePassword(
            @PathVariable String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        // Updates customer password and returns the updated customer as a DTO
        Employee updatedEmployee = employeeService.updateEmployeePassword(
                email, oldPassword, newPassword
        );
        return new EmployeeResponseDto(updatedEmployee);
    }

    /**
     * Delete an employee by email.
     *
     * @param email Employee's email.
     */
    @DeleteMapping("/employee/{email}")
    public String deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployee(email);
        return "Employee with email " + email + " deleted successfully.";

    }

    @PostMapping("/employee/login")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeResponseDto employeeLogin(@RequestParam String email, @RequestParam String password) {
        Employee employee = employeeService.getEmployee(email);

        // Check if the employee exists
        if (employee == null) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // Validate the password
        if (!passwordEncoder.matches(password, employee.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // Return employee details without the password
        return new EmployeeResponseDto(employee);
    }

}


