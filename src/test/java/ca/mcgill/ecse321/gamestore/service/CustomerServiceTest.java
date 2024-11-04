package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PersonRepository personRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private CustomerService customerService;

    private static final String VALID_USER_ID = "user123";
    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EMAIL = "john.doe@example.com";
    private static final String VALID_PASSWORD = "securepassword123";
    private static final String INVALID_EMAIL = "notanemail";
    private static final String EXISTING_EMAIL = "existing@example.com";

    @BeforeEach
    public void setUpMocks() {
        // Mock findCustomerByEmail for various cases
        lenient().when(customerRepository.findCustomerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String email = invocation.getArgument(0);
            if (email.equals(EXISTING_EMAIL)) {
                return new Customer("existingID", "Existing Name", email, "existingpassword");
            } else if (email.equals(VALID_EMAIL)) {
                return new Customer(VALID_USER_ID, VALID_NAME, email, VALID_PASSWORD);
            }
            return null; // Other emails simulate non-existence
        });

        // Mock findAll to return a list of customers
        lenient().when(customerRepository.findAll()).thenReturn(List.of(
                new Customer("user123", "John Doe", "john.doe@example.com", "password1"),
                new Customer("user456", "Jane Doe", "jane.doe@example.com", "password2")
        ));

        // Mock save to return the customer passed in, simulating a successful save
        lenient().when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

        @Test
    public void testCreateCustomer_Success() {
        Customer createdCustomer = customerService.createCustomer(VALID_USER_ID, VALID_NAME, "new.email@example.com", VALID_PASSWORD);
        assertNotNull(createdCustomer);
        assertEquals(VALID_USER_ID, createdCustomer.getUserID());
        assertEquals(VALID_NAME, createdCustomer.getName());
        assertEquals("new.email@example.com", createdCustomer.getEmail());
        assertEquals(VALID_PASSWORD, createdCustomer.getPassword());
    }

    @Test
    public void testCreateCustomer_InvalidEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                customerService.createCustomer(VALID_USER_ID, VALID_NAME, INVALID_EMAIL, VALID_PASSWORD));
        assertEquals("The email is invalid!", exception.getMessage());
    }

    @Test
    public void testCreateCustomer_ExistingEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                customerService.createCustomer(VALID_USER_ID, VALID_NAME, EXISTING_EMAIL, VALID_PASSWORD));
        assertEquals("User with that email already exists!", exception.getMessage());
    }

    @Test
    public void testGetCustomer_ExistingEmail() {
        Customer customer = customerService.getCustomer(VALID_EMAIL);
        assertNotNull(customer);
        assertEquals(VALID_EMAIL, customer.getEmail());
    }

    @Test
    public void testGetCustomer_NonExistingEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                customerService.getCustomer("nonexistent@example.com"));
        assertEquals("Customer Not Found", exception.getMessage());
    }

    @Test
    public void testGetAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        assertEquals(2, customers.size());
    }

    @Test
    public void testUpdateCustomerPassword_Success() {
        Customer updatedCustomer = customerService.updateCustomerPassword(VALID_EMAIL, VALID_PASSWORD, "newPassword123");
        assertNotNull(updatedCustomer);
        assertEquals("newPassword123", updatedCustomer.getPassword());
    }

    @Test
    public void testUpdateCustomerPassword_IncorrectOldPassword() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                customerService.updateCustomerPassword(VALID_EMAIL, "wrongOldPassword", "newPassword123"));
        assertEquals("Incorrect old password!", exception.getMessage());
    }

    @Test
    public void testDeleteCustomer_Success() {
        boolean isDeleted = customerService.deleteCustomer(VALID_EMAIL);
        assertTrue(isDeleted);
    }

    @Test
    public void testDeleteCustomer_NonExistingEmail() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                customerService.deleteCustomer("nonexistent@example.com"));
        assertEquals("Customer with that email does not exist!", exception.getMessage());
    }
}

