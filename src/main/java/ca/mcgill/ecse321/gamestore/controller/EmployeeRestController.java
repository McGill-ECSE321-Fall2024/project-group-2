package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.*;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST Controller for Employee-related operations.
 */
@RestController
@RequestMapping("/api/employees")
public class EmployeeRestController {

    @Autowired
    private EmployeeService employeeService;

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
    @GetMapping("/{email}")
    public EmployeeResponseDto getEmployeeByEmail(@PathVariable String email) {
        Employee employee = employeeService.getEmployeeByEmail(email);
        return new EmployeeResponseDto(employee);
    }

    /**
     * Create a new employee.
     *
     * @param employeeRequestDto EmployeeRequestDto containing the employee details.
     * @return EmployeeResponseDto of the created employee.
     */
    @PostMapping
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

    /**
     * Update an existing employee's details.
     *
     * @param email              Employee's email.
     * @param employeeRequestDto EmployeeRequestDto with new details.
     * @return EmployeeResponseDto of the updated employee.
     */
    @PutMapping("/{email}")
    public EmployeeResponseDto updateEmployee(
            @PathVariable String email,
            @RequestBody EmployeeRequestDto employeeRequestDto) {
        Employee employee = employeeService.updateEmployee(
                email,
                employeeRequestDto.getUserID(),
                employeeRequestDto.getName(),
                employeeRequestDto.getPassword()
        );
        return new EmployeeResponseDto(employee);
    }

    /**
     * Delete an employee by email.
     *
     * @param email Employee's email.
     */
    @DeleteMapping("/{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEmployee(@PathVariable String email) {
        employeeService.deleteEmployee(email);
    }



}


