package ca.mcgill.ecse321.gamestore.integration;
import ca.mcgill.ecse321.gamestore.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerListDto;
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

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        for (Customer customer : customerService.getAllCustomers()) {
            customerService.deleteCustomer(customer.getEmail());
        }
    }

    @Test
    public void testCreateCustomer() {
        CustomerRequestDto customerDto = new CustomerRequestDto(
                "user1", "John Doe", "john.doe@mail.com", "securePassword123"
        );

        ResponseEntity<CustomerResponseDto> responseEntity = restTemplate.postForEntity(
                "/customer", customerDto, CustomerResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        CustomerResponseDto createdCustomer = responseEntity.getBody();
        assertNotNull(createdCustomer);
        assertEquals("user1", createdCustomer.getUserID());
        assertEquals("John Doe", createdCustomer.getName());
        assertEquals("john.doe@mail.com", createdCustomer.getEmail());
    }

    @Test
    public void testCreateCustomerWithInvalidEmail() {
        CustomerRequestDto customerDto = new CustomerRequestDto(
                "user2", "Invalid Email User", "invalid-email", "password123"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/customer", customerDto, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The email is invalid!", responseEntity.getBody());
    }

    @Test
    public void testCreateCustomerWithExistingEmail() {
        customerService.createCustomer("user3", "Existing User", "existing@mail.com", "password123");
        CustomerRequestDto customerDto = new CustomerRequestDto(
                "user4", "Duplicate Email User", "existing@mail.com", "password456"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/customer", customerDto, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("User with that email already exists!", responseEntity.getBody());
    }

    @Test
    public void testGetCustomerByEmail() {
        customerService.createCustomer("user2", "Jane Smith", "jane.smith@mail.com", "anotherPassword123");

        ResponseEntity<CustomerResponseDto> responseEntity = restTemplate.getForEntity(
                "/customer/jane.smith@mail.com", CustomerResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        CustomerResponseDto retrievedCustomer = responseEntity.getBody();
        assertNotNull(retrievedCustomer);
        assertEquals("user2", retrievedCustomer.getUserID());
        assertEquals("Jane Smith", retrievedCustomer.getName());
        assertEquals("jane.smith@mail.com", retrievedCustomer.getEmail());
    }

    @Test
    public void testGetNonExistingCustomerByEmail() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/customer/non.existent@mail.com", String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Customer Not Found", responseEntity.getBody());
    }

    @Test
    public void testGetAllCustomers() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "password123");
        customerService.createCustomer("user2", "Jane Smith", "jane.smith@mail.com", "password456");

        ResponseEntity<CustomerListDto> responseEntity = restTemplate.getForEntity(
                "/customers", CustomerListDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().getCustomers().size());
    }

    @Test
    public void testUpdateCustomerPassword() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<CustomerResponseDto> responseEntity = restTemplate.exchange(
                "/customer/john.doe@mail.com/updatePassword?oldPassword=securePassword123&newPassword=newSecurePassword456",
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

    @Test
    public void testUpdateCustomerPasswordWithIncorrectOldPassword() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/customer/john.doe@mail.com/updatePassword?oldPassword=wrongOldPassword&newPassword=newPassword456",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Incorrect old password!", responseEntity.getBody());
    }

    @Test
    public void testUpdateCustomerPasswordWithEmptyNewPassword() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/customer/john.doe@mail.com/updatePassword?oldPassword=securePassword123&newPassword=",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The new password cannot be empty!", responseEntity.getBody());
    }

    @Test
    public void testDeleteCustomer() {
        customerService.createCustomer("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/customer/john.doe@mail.com", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Customer with email john.doe@mail.com deleted successfully.", responseEntity.getBody());

        assertThrows(GameStoreException.class, () -> customerService.getCustomer("john.doe@mail.com"));
    }

    @Test
    public void testDeleteNonExistingCustomer() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/customer/non.existent@mail.com", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Customer with that email does not exist!", responseEntity.getBody());
    }
}


