package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private OwnerRepository ownerRepository;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private static final String VALID_USER_ID = "user123";
    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EMAIL = "john.doe@example.com";
    private static final String VALID_PASSWORD = "securepassword123";
    private static final String INVALID_EMAIL = "notanemail";
    private static final String EXISTING_EMAIL = "existing@example.com";

    @BeforeEach
    public void setUpMocks() {
        // Mock behavior for finding an email in the employee repository
        lenient().when(employeeRepository.findEmployeeByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String email = invocation.getArgument(0);
            if (email.equals(EXISTING_EMAIL)) {
                return new Employee("existingID", "Existing Name", email, "existingpassword");
            } else if (email.equals(VALID_EMAIL)) {
                return new Employee(VALID_USER_ID, VALID_NAME, email, VALID_PASSWORD);
            }
            return null;
        });

        // Mock behavior for finding an email in other repositories
        lenient().when(customerRepository.findCustomerByEmail(EXISTING_EMAIL)).thenReturn(null);
        lenient().when(personRepository.findPersonByEmail(EXISTING_EMAIL)).thenReturn(new Employee("existingID", "Existing Name", EXISTING_EMAIL, "existingpassword"));
        lenient().when(ownerRepository.findOwnerByEmail(EXISTING_EMAIL)).thenReturn(null);
        lenient().when(accountRepository.findAccountByEmail(EXISTING_EMAIL)).thenReturn(null);

        // Mock behavior for retrieving all employees
        lenient().when(employeeRepository.findAll()).thenReturn(List.of(
                new Employee("user123", "John Doe", "john.doe@example.com", "password1"),
                new Employee("user456", "Jane Doe", "jane.doe@example.com", "password2")
        ));

        // Mock behavior for saving an employee
        lenient().when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testCreateEmployee_Success() {
        Employee createdEmployee = employeeService.createEmployee(VALID_USER_ID, VALID_NAME, "new.email@example.com", VALID_PASSWORD);
        assertNotNull(createdEmployee);
        assertEquals(VALID_USER_ID, createdEmployee.getUserID());
        assertEquals(VALID_NAME, createdEmployee.getName());
        assertEquals("new.email@example.com", createdEmployee.getEmail());
        assertEquals(VALID_PASSWORD, createdEmployee.getPassword());
    }

    @Test
    public void testCreateEmployee_InvalidEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, VALID_NAME, INVALID_EMAIL, VALID_PASSWORD));
        assertEquals("The email is invalid!", exception.getMessage());
    }

    @Test
    public void testCreateEmployee_ExistingEmailInRepositories() {
        // Test that an exception is thrown if the email exists in any repository
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, VALID_NAME, EXISTING_EMAIL, VALID_PASSWORD));
        assertEquals("User with that email already exists!", exception.getMessage());
    }

    @Test
    public void testGetEmployee_ExistingEmail() {
        Employee employee = employeeService.getEmployee(VALID_EMAIL);
        assertNotNull(employee);
        assertEquals(VALID_EMAIL, employee.getEmail());
    }

    @Test
    public void testGetEmployee_NonExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.getEmployee("nonexistent@example.com"));
        assertEquals("Employee Not Found", exception.getMessage());
    }

    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(2, employees.size());
    }

    @Test
    public void testUpdateEmployeePassword_Success() {
        Employee updatedEmployee = employeeService.updateEmployeePassword(VALID_EMAIL, VALID_PASSWORD, "newPassword123");
        assertNotNull(updatedEmployee);
        assertEquals("newPassword123", updatedEmployee.getPassword());
    }

    @Test
    public void testUpdateEmployeePassword_IncorrectOldPassword() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.updateEmployeePassword(VALID_EMAIL, "wrongOldPassword", "newPassword123"));
        assertEquals("Incorrect old password!", exception.getMessage());
    }

    @Test
    public void testDeleteEmployee_Success() {
        boolean isDeleted = employeeService.deleteEmployee(VALID_EMAIL);
        assertTrue(isDeleted);
    }

    @Test
    public void testDeleteEmployee_NonExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.deleteEmployee("nonexistent@example.com"));
        assertEquals("Employee with that email does not exist!", exception.getMessage());
    }
}






