package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.*;
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
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private PasswordEncoder passwordEncoder; // Mocking the PasswordEncoder
    @Mock
    private CustomerRepository customerRepository; // Mocking the CustomerRepository
    @Mock
    private PersonRepository personRepository; // Mocking the PersonRepository
    @Mock
    private EmployeeRepository employeeRepository; // Mocking the EmployeeRepository
    @Mock
    private OwnerRepository ownerRepository; // Mocking the OwnerRepository
    @Mock
    private AccountRepository accountRepository; // Mocking the AccountRepository

    @InjectMocks
    private CustomerService customerService; // Injecting mocks into CustomerService

    private static final String VALID_USER_ID = "user123"; // Valid user ID for tests
    private static final String VALID_NAME = "John Doe"; // Valid name for tests
    private static final String VALID_EMAIL = "john.doe@example.com"; // Valid email for tests
    private static final String VALID_PASSWORD = "securepassword123"; // Valid password for tests
    private static final String INVALID_EMAIL = "notanemail"; // Invalid email for tests
    private static final String EXISTING_EMAIL = "existing@example.com"; // Existing email for conflict
    private static final String ENCODED_PASSWORD = "encodedpassword"; // Simulated encoded password


    @BeforeEach
    public void setUpMocks() {
        // Mock behavior for finding an email in the customer repository
        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String email = invocation.getArgument(0);
            if (email.equals(EXISTING_EMAIL)) {
                return new Customer("existingID", "Existing Name", email, "existingpassword");
            } else if (email.equals(VALID_EMAIL)) {
                return new Customer(VALID_USER_ID, VALID_NAME, email, VALID_PASSWORD);
            }
            return null;
        });

        // Mock behavior for finding an email in other repositories
        lenient().when(personRepository.findPersonByEmail(EXISTING_EMAIL)).thenReturn(new Customer("existingID", "Existing Name", EXISTING_EMAIL, "existingpassword"));
        lenient().when(accountRepository.findAccountByEmail(EXISTING_EMAIL)).thenReturn(null);

        // Mock behavior for retrieving all customers
        lenient().when(customerRepository.findAll()).thenReturn(List.of(
                new Customer("user123", "John Doe", "john.doe@example.com", "password1"),
                new Customer("user456", "Jane Doe", "jane.doe@example.com", "password2")
        ));

        // Mock behavior for saving a customer
        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Mock password encoding behavior
        lenient().when(passwordEncoder.encode(anyString())).thenReturn(ENCODED_PASSWORD); // Simulate password encoding
        lenient().when(passwordEncoder.matches(anyString(), anyString())).thenAnswer(invocation -> {
            String rawPassword = invocation.getArgument(0);
            String encodedPassword = invocation.getArgument(1);
            return rawPassword.equals(encodedPassword); // Simple match check for tests
        });
    }

    // Test case to check customer creation success
    @Test
    public void testCreateCustomer_Success() {
        Customer createdCustomer = customerService.createCustomer(VALID_USER_ID, VALID_NAME, "new.email@example.com", VALID_PASSWORD);
        assertNotNull(createdCustomer);
        assertEquals(VALID_USER_ID, createdCustomer.getUserID());
        assertEquals(VALID_NAME, createdCustomer.getName());
        assertEquals("new.email@example.com", createdCustomer.getEmail());
        assertEquals(ENCODED_PASSWORD, createdCustomer.getPassword());
    }

    // Test case to check customer creation with invalid email
    @Test
    public void testCreateCustomer_InvalidEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.createCustomer(VALID_USER_ID, VALID_NAME, INVALID_EMAIL, VALID_PASSWORD));
        assertEquals("The email is invalid!", exception.getMessage());
    }

    // Test case to check customer creation with an existing email in the repository
    @Test
    public void testCreateCustomer_ExistingEmailInRepositories() {
        // Test that an exception is thrown if the email exists in any repository
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.createCustomer(VALID_USER_ID, VALID_NAME, EXISTING_EMAIL, VALID_PASSWORD));
        assertEquals("User with that email already exists!", exception.getMessage());
    }

    // Test case to retrieve a customer by email when the customer exists
    @Test
    public void testGetCustomer_ExistingEmail() {
        Customer customer = customerService.getCustomer(VALID_EMAIL);
        assertNotNull(customer);
        assertEquals(VALID_EMAIL, customer.getEmail());
    }

    // Test case to retrieve a customer by email when the customer does not exist
    @Test
    public void testGetCustomer_NonExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.getCustomer("nonexistent@example.com"));
        assertEquals("Customer Not Found", exception.getMessage());
    }

    // Test case to retrieve all customers
    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(2, customers.size());
    }

    // Test case to check if customer password update is successful
    @Test
    public void testUpdateCustomerPassword_Success() {
        Customer updatedCustomer = customerService.updateCustomerPassword(VALID_EMAIL, VALID_PASSWORD, "newPassword123");
        assertNotNull(updatedCustomer);
        assertEquals(ENCODED_PASSWORD, updatedCustomer.getPassword());
    }

    // Test case to check if updating password with an incorrect old password fails
    @Test
    public void testUpdateCustomerPassword_IncorrectOldPassword() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.updateCustomerPassword(VALID_EMAIL, "wrongOldPassword", "newPassword123"));
        assertEquals("Incorrect old password!", exception.getMessage());
    }

    // Test case to delete a customer successfully
    @Test
    public void testDeleteCustomer_Success() {
        boolean isDeleted = customerService.deleteCustomer(VALID_EMAIL);
        assertTrue(isDeleted);
    }

    // Test case to check if attempting to delete a non-existing customer fails
    @Test
    public void testDeleteCustomer_NonExistingEmail() {
        Exception exception = assertThrows(GameStoreException.class, () ->
                customerService.deleteCustomer("nonexistent@example.com"));
        assertEquals("Customer with that email does not exist!", exception.getMessage());
    }
}

