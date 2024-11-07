package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Customer;
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
public class CustomerServiceTest {

    // Mocked dependencies for CustomerService
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

    // The service being tested, with injected mocks
    @InjectMocks
    private CustomerService customerService;

    // Constants for use in tests
    private static final String VALID_USER_ID = "user123";
    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EMAIL = "john.doe@example.com";
    private static final String VALID_PASSWORD = "securepassword123";
    private static final String INVALID_EMAIL = "notanemail";
    private static final String EXISTING_EMAIL = "existing@example.com";

    // Sets up mock behaviors before each test
    @BeforeEach
    public void setUpMocks() {
        // Mock behavior for finding a customer by email
        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String email = invocation.getArgument(0);
            if (email.equals(EXISTING_EMAIL)) {
                return new Customer("existingID", "Existing Name", email, "existingpassword");
            } else if (email.equals(VALID_EMAIL)) {
                return new Customer(VALID_USER_ID, VALID_NAME, email, VALID_PASSWORD);
            }
            return null; // Simulate non-existence for other emails
        });

        // Mock behavior for retrieving all customers
        lenient().when(customerRepository.findAll()).thenReturn(List.of(
                new Customer("user123", "John Doe", "john.doe@example.com", "password1"),
                new Customer("user456", "Jane Doe", "jane.doe@example.com", "password2")
        ));

        // Mock behavior for saving a customer
        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    // Test successful customer creation
    @Test
    public void testCreateCustomer_Success() {
        Customer createdCustomer = customerService.createCustomer(VALID_USER_ID, VALID_NAME, "new.email@example.com", VALID_PASSWORD);
        assertNotNull(createdCustomer);
        assertEquals(VALID_USER_ID, createdCustomer.getUserID());
        assertEquals(VALID_NAME, createdCustomer.getName());
        assertEquals("new.email@example.com", createdCustomer.getEmail());
        assertEquals(VALID_PASSWORD, createdCustomer.getPassword());
    }

    // Test creating a customer with an invalid email format
    @Test
    public void testCreateCustomer_InvalidEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.createCustomer(VALID_USER_ID, VALID_NAME, INVALID_EMAIL, VALID_PASSWORD));
        assertEquals("The email is invalid!", exception.getMessage());
    }

    // Test creating a customer with an email that already exists
    @Test
    public void testCreateCustomer_ExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.createCustomer(VALID_USER_ID, VALID_NAME, EXISTING_EMAIL, VALID_PASSWORD));
        assertEquals("User with that email already exists!", exception.getMessage());
    }

    // Test retrieving a customer by email when the customer exists
    @Test
    public void testGetCustomer_ExistingEmail() {
        Customer customer = customerService.getCustomer(VALID_EMAIL);
        assertNotNull(customer);
        assertEquals(VALID_EMAIL, customer.getEmail());
    }

    // Test retrieving a customer by email when the customer does not exist
    @Test
    public void testGetCustomer_NonExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.getCustomer("nonexistent@example.com"));
        assertEquals("Customer Not Found", exception.getMessage());
    }

    // Test retrieving all customers
    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(2, customers.size());
    }

    // Test updating a customer's password successfully
    @Test
    public void testUpdateCustomerPassword_Success() {
        Customer updatedCustomer = customerService.updateCustomerPassword(VALID_EMAIL, VALID_PASSWORD, "newPassword123");
        assertNotNull(updatedCustomer);
        assertEquals("newPassword123", updatedCustomer.getPassword());
    }

    // Test updating a customer's password with an incorrect old password
    @Test
    public void testUpdateCustomerPassword_IncorrectOldPassword() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.updateCustomerPassword(VALID_EMAIL, "wrongOldPassword", "newPassword123"));
        assertEquals("Incorrect old password!", exception.getMessage());
    }

    // Test deleting a customer successfully
    @Test
    public void testDeleteCustomer_Success() {
        boolean isDeleted = customerService.deleteCustomer(VALID_EMAIL);
        assertTrue(isDeleted);
    }

    // Test deleting a customer that does not exist
    @Test
    public void testDeleteCustomer_NonExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.deleteCustomer("nonexistent@example.com"));
        assertEquals("Customer with that email does not exist!", exception.getMessage());
    }
}


