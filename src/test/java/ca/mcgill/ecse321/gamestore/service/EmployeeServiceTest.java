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
import org.mockito.stubbing.Answer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for EmployeeService.
 */
@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    // Mocked repository dependencies
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private OwnerRepository ownerRepository;

    // Inject mocks into the EmployeeService
    @InjectMocks
    private EmployeeService employeeService;

    // Constants for commonly used test data
    private static final String VALID_USER_ID = "user123";
    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EMAIL = "john.doe@example.com";
    private static final String VALID_PASSWORD = "securepassword123";
    private static final String EXISTING_EMAIL = "existing@example.com";

    /**
     * Sets up mock outputs for testing.
     * This method is called before each test to define behavior for mocked methods.
     */
    @BeforeEach
    public void setMockOutput() {
        Employee existingEmployee = new Employee(VALID_USER_ID, VALID_NAME, EXISTING_EMAIL, VALID_PASSWORD);

        // Mocks finding an employee by email, returns the existing employee if the email matches
        lenient().when(employeeRepository.findEmployeeByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String email = invocation.getArgument(0);
            if (email.equals(EXISTING_EMAIL)) return existingEmployee;
            return null;
        });

        // Mocks saving an employee, returns the employee instance provided
        lenient().when(employeeRepository.save(any(Employee.class))).thenAnswer((Answer<Employee>) invocation -> invocation.getArgument(0));
    }

    /**
     * Test creating a new employee with valid data.
     * Verifies that a new employee is created successfully with expected properties.
     */
    @Test
    public void testCreateEmployeeSuccess() {
        Employee employee = employeeService.createEmployee(VALID_USER_ID, VALID_NAME, "new.email@example.com", VALID_PASSWORD);
        assertNotNull(employee);
        assertEquals(VALID_NAME, employee.getName());
        assertEquals("new.email@example.com", employee.getEmail());
        assertEquals(VALID_USER_ID, employee.getUserID());
    }

    /**
     * Test creating an employee with an existing email.
     * Verifies that an exception is thrown if the email is already used by another employee.
     */
    @Test
    public void testCreateEmployeeExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, VALID_NAME, EXISTING_EMAIL, VALID_PASSWORD));
        assertEquals("An employee or user with this email already exists.", exception.getMessage());
    }

    /**
     * Test creating an employee with a short password.
     * Verifies that an exception is thrown if the password is shorter than 8 characters.
     */
    @Test
    public void testCreateEmployeeShortPassword() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, VALID_NAME, VALID_EMAIL, "short"));
        assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    /**
     * Test creating an employee with empty name.
     * Verifies that an exception is thrown if the name is empty.
     */
    @Test
    public void testCreateEmployeeEmptyName() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, "", VALID_EMAIL, VALID_PASSWORD));
        assertEquals("Employee name cannot be empty.", exception.getMessage());
    }

    /**
     * Test creating an employee with null email.
     * Verifies that an exception is thrown if the email is null.
     */
    @Test
    public void testCreateEmployeeNullEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, VALID_NAME, null, VALID_PASSWORD));
        assertEquals("Email cannot be null or empty.", exception.getMessage());
    }

    /**
     * Test creating an employee with whitespace-only password.
     * Verifies that an exception is thrown if the password consists only of whitespace.
     */
    @Test
    public void testCreateEmployeeWhitespacePassword() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.createEmployee(VALID_USER_ID, VALID_NAME, VALID_EMAIL, "        "));
        assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    /**
     * Test creating an employee with special characters in name.
     * Verifies that special characters in the name are accepted and retained.
     */
    @Test
    public void testCreateEmployeeSpecialCharactersInName() {
        Employee employee = employeeService.createEmployee(VALID_USER_ID, "John@Doe!", VALID_EMAIL, VALID_PASSWORD);
        assertNotNull(employee);
        assertEquals("John@Doe!", employee.getName());
    }

    /**
     * Test retrieving an employee by email.
     * Verifies that an employee with a specific email is retrieved successfully.
     */
    @Test
    public void testGetEmployeeByEmailSuccess() {
        Employee employee = employeeService.getEmployeeByEmail(EXISTING_EMAIL);
        assertNotNull(employee);
        assertEquals(VALID_NAME, employee.getName());
    }

    /**
     * Test retrieving an employee by trimmed email.
     * Verifies that leading/trailing whitespace in the email is ignored.
     */
    @Test
    public void testGetEmployeeByTrimmedEmail() {
        Employee employee = employeeService.getEmployeeByEmail("   " + EXISTING_EMAIL + "   ");
        assertNotNull(employee);
        assertEquals(VALID_NAME, employee.getName());
    }

    /**
     * Test updating an employee without any actual changes.
     * Verifies that updating with identical details does not cause issues.
     */
    @Test
    public void testUpdateEmployeeNoChanges() {
        Employee updatedEmployee = employeeService.updateEmployee(EXISTING_EMAIL, VALID_USER_ID, VALID_NAME, VALID_PASSWORD);
        assertNotNull(updatedEmployee);
        assertEquals(VALID_NAME, updatedEmployee.getName());
    }

    /**
     * Test updating an employee with null password.
     * Verifies that an exception is thrown if the password is null.
     */
    @Test
    public void testUpdateEmployeeNullPassword() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.updateEmployee(EXISTING_EMAIL, VALID_USER_ID, VALID_NAME, null));
        assertEquals("Password must be at least 8 characters long.", exception.getMessage());
    }

    /**
     * Test deleting an employee by null email.
     * Verifies that an exception is thrown if the email is null when deleting an employee.
     */
    @Test
    public void testDeleteEmployeeByNullEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.deleteEmployee(null));
        assertEquals("Email cannot be null or empty.", exception.getMessage());
    }

    /**
     * Test retrieving a non-existing employee by email.
     * Verifies that an exception is thrown if the email does not correspond to an existing employee.
     */
    @Test
    public void testGetEmployeeByEmailNotFound() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.getEmployeeByEmail("nonexistent@example.com"));
        assertEquals("No employee found with email: nonexistent@example.com", exception.getMessage());
    }

    /**
     * Test updating an employee's details.
     * Verifies that an employee's details can be updated successfully.
     */
    @Test
    public void testUpdateEmployeeSuccess() {
        Employee updatedEmployee = employeeService.updateEmployee(EXISTING_EMAIL, VALID_USER_ID, "New Name", "newpassword123");
        assertNotNull(updatedEmployee);
        assertEquals("New Name", updatedEmployee.getName());
    }

    /**
     * Test deleting an existing employee.
     * Verifies that an employee can be deleted successfully.
     */
    @Test
    public void testDeleteEmployeeSuccess() {
        employeeService.deleteEmployee(EXISTING_EMAIL);
        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }

    /**
     * Test deleting a non-existing employee.
     * Verifies that an exception is thrown if the employee to be deleted does not exist.
     */
    @Test
    public void testDeleteEmployeeNotFound() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                employeeService.deleteEmployee("nonexistent@example.com"));
        assertEquals("No employee found with email: nonexistent@example.com", exception.getMessage());
    }
}





