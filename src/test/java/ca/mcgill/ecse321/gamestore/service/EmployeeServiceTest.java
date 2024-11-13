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
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    // Mock dependencies for the service
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
    @Mock
    private PasswordEncoder passwordEncoder;  // Mock PasswordEncoder instead of PasswordHasher

    @InjectMocks
    private EmployeeService employeeService;  // Inject the mock objects into the service

    // Constants used for tests
    private static final String VALID_USER_ID = "user123";
    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EMAIL = "john.doe@example.com";
    private static final String VALID_PASSWORD = "securepassword123";
    private static final String INVALID_EMAIL = "notanemail";
    private static final String EXISTING_EMAIL = "existing@example.com";
    private static final String ENCODED_PASSWORD = "encodedpassword";

    // Set up mock behavior before each test
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
        lenient().when(personRepository.findPersonByEmail(EXISTING_EMAIL)).thenReturn(new Employee("existingID", "Existing Name", EXISTING_EMAIL, "existingpassword"));
        lenient().when(accountRepository.findAccountByEmail(EXISTING_EMAIL)).thenReturn(null);

        // Mock behavior for retrieving all employees
        lenient().when(employeeRepository.findAll()).thenReturn(List.of(
                new Employee("user123", "John Doe", "john.doe@example.com", "password1"),
                new Employee("user456", "Jane Doe", "jane.doe@example.com", "password2")
        ));

        // Mock behavior for saving an employee
        lenient().when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Mock password encoding behavior
        lenient().when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD); // Simulate password encoding
        lenient().when(passwordEncoder.matches(anyString(), anyString())).thenAnswer(invocation -> {
            String rawPassword = invocation.getArgument(0);
            String encodedPassword = invocation.getArgument(1);
            return rawPassword.equals(encodedPassword); // Simple match check for tests
        });
    }

    // Test the creation of a new employee with valid data
    @Test
    public void testCreateEmployee_Success() {
        Employee createdEmployee = employeeService.createEmployee(VALID_USER_ID, VALID_NAME, "new.email@example.com", VALID_PASSWORD);
        assertNotNull(createdEmployee);
        assertEquals(VALID_USER_ID, createdEmployee.getUserID());
        assertEquals(VALID_NAME, createdEmployee.getName());
        assertEquals("new.email@example.com", createdEmployee.getEmail());
        assertEquals(ENCODED_PASSWORD, createdEmployee.getPassword()); // Ensure password is encoded
    }

    // Test that an exception is thrown for an invalid email format
    @Test
    public void testCreateEmployee_InvalidEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, VALID_NAME, INVALID_EMAIL, VALID_PASSWORD));
        assertEquals("The email is invalid!", exception.getMessage());
    }

    // Test that an exception is thrown if the email already exists in the repositories
    @Test
    public void testCreateEmployee_ExistingEmailInRepositories() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, VALID_NAME, EXISTING_EMAIL, VALID_PASSWORD));
        assertEquals("User with that email already exists!", exception.getMessage());
    }

    // Test retrieval of an employee by their existing email
    @Test
    public void testGetEmployee_ExistingEmail() {
        Employee employee = employeeService.getEmployee(VALID_EMAIL);
        assertNotNull(employee);
        assertEquals(VALID_EMAIL, employee.getEmail());
    }

    // Test that an exception is thrown when trying to retrieve an employee that doesn't exist
    @Test
    public void testGetEmployee_NonExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.getEmployee("nonexistent@example.com"));
        assertEquals("Employee Not Found", exception.getMessage());
    }

    // Test retrieving all employees
    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = employeeService.getAllEmployees();
        assertEquals(2, employees.size());
    }

    // Test updating an employee's password with the correct old password
    @Test
    public void testUpdateEmployeePassword_Success() {
        Employee updatedEmployee = employeeService.updateEmployeePassword(VALID_EMAIL, VALID_PASSWORD, "newPassword123");
        assertNotNull(updatedEmployee);
        assertEquals(ENCODED_PASSWORD, updatedEmployee.getPassword()); // Ensure new password is encoded
    }

    // Test that an exception is thrown if the old password does not match
    @Test
    public void testUpdateEmployeePassword_IncorrectOldPassword() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.updateEmployeePassword(VALID_EMAIL, "wrongOldPassword", "newPassword123"));
        assertEquals("Incorrect old password!", exception.getMessage());
    }

    // Test successful deletion of an employee
    @Test
    public void testDeleteEmployee_Success() {
        boolean isDeleted = employeeService.deleteEmployee(VALID_EMAIL);
        assertTrue(isDeleted);
    }

    // Test that an exception is thrown when trying to delete a non-existent employee
    @Test
    public void testDeleteEmployee_NonExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.deleteEmployee("nonexistent@example.com"));
        assertEquals("Employee with that email does not exist!", exception.getMessage());
    }
}
