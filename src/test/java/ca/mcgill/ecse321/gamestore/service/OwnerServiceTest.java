package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Owner;
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
public class OwnerServiceTest {

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
    private OwnerService ownerService;

    // Constants used to represent valid and invalid data in test cases
    private static final String VALID_USER_ID = "user123";
    private static final String VALID_NAME = "John Doe";
    private static final String VALID_EMAIL = "john.doe@example.com";
    private static final String VALID_PASSWORD = "securepassword123";
    private static final String INVALID_EMAIL = "notanemail";
    private static final String EXISTING_EMAIL = "existing@example.com";

    @BeforeEach
    public void setUpMocks() {
        // Mock ownerRepository.findOwnerByEmail to return specific Owner objects for certain emails
        lenient().when(ownerRepository.findOwnerByEmail(anyString())).thenAnswer((InvocationOnMock invocation) -> {
            String email = invocation.getArgument(0);
            if (email.equals(EXISTING_EMAIL)) {
                return new Owner("existingID", "Existing Name", email, "existingpassword");
            } else if (email.equals(VALID_EMAIL)) {
                return new Owner(VALID_USER_ID, VALID_NAME, email, VALID_PASSWORD);
            }
            return null; // Return null for non-existent emails
        });

        // Mock ownerRepository.findAll to return a predefined list of Owner objects
        lenient().when(ownerRepository.findAll()).thenReturn(List.of(
                new Owner("user123", "John Doe", "john.doe@example.com", "password1"),
                new Owner("user456", "Jane Doe", "jane.doe@example.com", "password2")
        ));

        // Mock ownerRepository.save to return the Owner object that was passed in, simulating a save operation
        lenient().when(ownerRepository.save(any(Owner.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testCreateOwner_Success() {
        // Test the successful creation of a new owner
        Owner createdOwner = ownerService.createOwner(VALID_USER_ID, VALID_NAME, "new.email@example.com", VALID_PASSWORD);
        assertNotNull(createdOwner);  // Verify that the created owner is not null
        assertEquals(VALID_USER_ID, createdOwner.getUserID());
        assertEquals(VALID_NAME, createdOwner.getName());
        assertEquals("new.email@example.com", createdOwner.getEmail());
        assertEquals(VALID_PASSWORD, createdOwner.getPassword());
    }

    @Test
    public void testCreateOwner_InvalidEmail() {
        // Test creation of an owner with an invalid email, expecting an IllegalArgumentException
        Exception exception = assertThrows(GameStoreException.class, () ->
                ownerService.createOwner(VALID_USER_ID, VALID_NAME, INVALID_EMAIL, VALID_PASSWORD));
        assertEquals("The email is invalid!", exception.getMessage());
    }

    @Test
    public void testCreateOwner_ExistingEmail() {
        // Test creation of an owner with an existing email, expecting an IllegalArgumentException
        Exception exception = assertThrows(GameStoreException.class, () ->
                ownerService.createOwner(VALID_USER_ID, VALID_NAME, EXISTING_EMAIL, VALID_PASSWORD));
        assertEquals("User with that email already exists!", exception.getMessage());
    }

    @Test
    public void testGetOwner_ExistingEmail() {
        // Test retrieving an owner by an existing email
        Owner customer = ownerService.getOwner(VALID_EMAIL);
        assertNotNull(customer);  // Verify that the retrieved owner is not null
        assertEquals(VALID_EMAIL, customer.getEmail());
    }

    @Test
    public void testGetOwner_NonExistingEmail() {
        // Test retrieving an owner by a non-existing email, expecting an IllegalArgumentException
        Exception exception = assertThrows(GameStoreException.class, () ->
                ownerService.getOwner("nonexistent@example.com"));
        assertEquals("Owner not found", exception.getMessage());
    }

    @Test
    public void testGetAllOwner() {
        // Test retrieving all owners and verify the returned list size
        Iterable<Owner> owners = ownerService.findAllOwner();
        List<Owner> list = new ArrayList<>();

        // Populate the list to check its size
        for (Owner item : owners) {
            list.add(item);
        }
        assertEquals(2, list.size());  // Expecting 2 owners in the list based on the mocked data
    }

    @Test
    public void testUpdateOwnerPassword_Success() {
        // Test updating an owner's password with the correct old password
        Owner updatedCustomer = ownerService.updateOwnerPassword(VALID_EMAIL, VALID_PASSWORD, "newPassword123");
        assertNotNull(updatedCustomer);  // Verify that the updated owner is not null
        assertEquals("newPassword123", updatedCustomer.getPassword());
    }

    @Test
    public void testUpdateOwnerPassword_IncorrectOldPassword() {
        // Test updating an owner's password with an incorrect old password, expecting an IllegalArgumentException
        Exception exception = assertThrows(GameStoreException.class, () ->
                ownerService.updateOwnerPassword(VALID_EMAIL, "wrongOldPassword", "newPassword123"));
        assertEquals("Incorrect old password!", exception.getMessage());
    }
}
