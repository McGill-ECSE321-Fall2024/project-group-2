package ca.mcgill.ecse321.gamestore.integration;

import ca.mcgill.ecse321.gamestore.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.service.CustomerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CustomerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerService customerService;

    // Clears the database before and after each test to ensure a clean state
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        for (Customer customer : customerService.getAllCustomers()) {
            customerService.deleteCustomer(customer.getEmail());
        }
    }

    // Test creating a new customer
    @Test
    public void testCreateCustomer() {
        CustomerRequestDto customerDto = new CustomerRequestDto(
                "user1", "John Doe", "john.doe@mail.com", "securePassword123"
        );

        ResponseEntity<CustomerResponseDto> responseEntity = restTemplate.postForEntity(
                "/customers", customerDto, CustomerResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CustomerResponseDto createdCustomer = responseEntity.getBody();
        assertNotNull(createdCustomer);
        assertEquals("user1", createdCustomer.getUserID());
        assertEquals("John Doe", createdCustomer.getName());
        assertEquals("john.doe@mail.com", createdCustomer.getEmail());
    }

    // Test creating a customer with an invalid email format
    @Test
    public void testCreateCustomerWithInvalidEmail() {
        CustomerRequestDto customerDto = new CustomerRequestDto(
                "user2", "Invalid Email User", "invalid-email", "password123"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/customers", customerDto, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The email is invalid!", responseEntity.getBody());
    }

    // Test creating a customer with an existing email
    @Test
    public void testCreateCustomerWithExistingEmail() {
        customerService.createCustomer("user3", "Existing User", "existing@mail.com", "password123");
        CustomerRequestDto customerDto = new CustomerRequestDto(
                "user4", "Duplicate Email User", "existing@mail.com", "password456"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/customers", customerDto, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode()); // Changed from BAD_REQUEST to CONFLICT
        assertEquals("User with that email already exists!", responseEntity.getBody());
    }

    // Test retrieving a customer by email
    @Test
    public void testGetCustomerByEmail() {
        customerService.createCustomer("user2", "Jane Smith", "jane.smith@mail.com", "anotherPassword123");

        ResponseEntity<CustomerResponseDto> responseEntity = restTemplate.getForEntity(
                "/customers/jane.smith@mail.com", CustomerResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        CustomerResponseDto retrievedCustomer = responseEntity.getBody();
        assertNotNull(retrievedCustomer);
        assertEquals("user2", retrievedCustomer.getUserID());
        assertEquals("Jane Smith", retrievedCustomer.getName());
        assertEquals("jane.smith@mail.com", retrievedCustomer.getEmail());
    }

    // Test retrieving a non-existing customer by email
    @Test
    public void testGetNonExistingCustomerByEmail() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/customers/non.existent@mail.com", String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Customer Not Found", responseEntity.getBody()); // Ensure message matches expected case
    }

    // Test updating a customer's password
    @Test
    public void testUpdateCustomerPassword() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<CustomerResponseDto> responseEntity = restTemplate.exchange(
                "/customers/john.doe@mail.com/updatePassword?oldPassword=securePassword123&newPassword=newSecurePassword456",
                org.springframework.http.HttpMethod.PUT,
                null,
                CustomerResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        CustomerResponseDto updatedCustomer = responseEntity.getBody();
        assertNotNull(updatedCustomer);
        assertEquals("john.doe@mail.com", updatedCustomer.getEmail());
    }

    // Test updating a customer's password with an incorrect old password
    @Test
    public void testUpdateCustomerPasswordWithIncorrectOldPassword() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/customers/john.doe@mail.com/updatePassword?oldPassword=wrongOldPassword&newPassword=newPassword456",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Incorrect old password!", responseEntity.getBody());
    }

    // Test updating a customer's password with an empty new password
    @Test
    public void testUpdateCustomerPasswordWithEmptyNewPassword() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/customers/john.doe@mail.com/updatePassword?oldPassword=securePassword123&newPassword=",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The new password cannot be empty!", responseEntity.getBody());
    }

    // Test deleting a customer
    @Test
    public void testDeleteCustomer() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/customers/john.doe@mail.com", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Customer with email john.doe@mail.com deleted successfully.", responseEntity.getBody());

        // Expect GameStoreException instead of IllegalArgumentException
        assertThrows(GameStoreException.class, () -> customerService.getCustomer("john.doe@mail.com"));
    }

    // Test deleting a non-existing customer
    @Test
    public void testDeleteNonExistingCustomer() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/customers/non.existent@mail.com", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Customer with that email does not exist!", responseEntity.getBody());
    }
}

