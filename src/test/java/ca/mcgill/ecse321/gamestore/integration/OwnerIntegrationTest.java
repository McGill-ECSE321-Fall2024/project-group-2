package ca.mcgill.ecse321.gamestore.integration;

import ca.mcgill.ecse321.gamestore.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gamestore.dto.OwnerDto;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.service.OwnerService;
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
public class OwnerIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OwnerService ownerService;

    // Clears the database before and after each test to ensure a clean state
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        // Delete all owners from the database to ensure no residual data
        for (Owner owner : ownerService.findAllOwner()) {
            ownerService.deleteOwner(owner.getEmail());
        }
    }

    // Test creating a new owner
    @Test
    public void testCreateOwner() {
        // Prepare the ownerDto to be sent in the request body
        OwnerDto ownerDto = new OwnerDto(
                "user1", "John Doe", "john.doe@mail.com", "securePassword123"
        );

        // Send a POST request to create a new owner
        ResponseEntity<OwnerDto> responseEntity = restTemplate.postForEntity(
                "/owner/", ownerDto, OwnerDto.class
        );

        // Assert that the response is not null and the status code is CREATED (201)
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        // Retrieve and assert that the created owner information is correct
        OwnerDto createdOwner = responseEntity.getBody();
        assertNotNull(createdOwner);
        assertEquals("user1", createdOwner.getUserID());
        assertEquals("John Doe", createdOwner.getName());
        assertEquals("john.doe@mail.com", createdOwner.getEmail());
    }

    // Test creating an owner with an invalid email format
    @Test
    public void testCreateOwnerWithInvalidEmail() {
        // Prepare the ownerDto with an invalid email format
        OwnerDto customerDto = new OwnerDto(
                "user2", "Invalid Email User", "invalid-email", "password123"
        );

        // Send a POST request and expect a BAD_REQUEST (400) response due to invalid email
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/owner", customerDto, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The email is invalid!", responseEntity.getBody());
    }

    // Test creating an owner with an existing email
    @Test
    public void testCreateOwnerWithExistingEmail() {
        // First, create an owner with the email "existing@mail.com"
        ownerService.createOwner("user3", "Existing User", "existing@mail.com", "password123");

        // Prepare the ownerDto with the same email "existing@mail.com"
        OwnerDto ownerDto = new OwnerDto(
                "user4", "Duplicate Email User", "existing@mail.com", "password456"
        );

        // Send a POST request and expect a CONFLICT (409) response due to existing email
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/owner", ownerDto, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode()); // Changed from BAD_REQUEST to CONFLICT
        assertEquals("User with that email already exists!", responseEntity.getBody());
    }

    // Test retrieving an owner by email
    @Test
    public void testGetOwnerByEmail() {
        // Create an owner with the email "jane.smith@mail.com"
        ownerService.createOwner("user2", "Jane Smith", "jane.smith@mail.com", "anotherPassword123");

        // Send a GET request to retrieve the owner by their email
        ResponseEntity<OwnerDto> responseEntity = restTemplate.getForEntity(
                "/owner/jane.smith@mail.com", OwnerDto.class
        );

        // Assert the response is OK (200) and the body contains the correct owner information
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        OwnerDto retrievedOwner = responseEntity.getBody();
        assertNotNull(retrievedOwner);
        assertEquals("user2", retrievedOwner.getUserID());
        assertEquals("Jane Smith", retrievedOwner.getName());
        assertEquals("jane.smith@mail.com", retrievedOwner.getEmail());
    }

    // Test retrieving a non-existing owner by email
    @Test
    public void testGetNonExistingOwnerByEmail() {
        // Try to get an owner by a non-existing email
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/owner/non.existent@mail.com", String.class
        );

        // Assert that the response is NOT_FOUND (404) and the message is correct
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Owner not found", responseEntity.getBody()); // Ensure message matches expected case
    }

    // Test updating an owner's password
    @Test
    public void testUpdateOwnerPassword() {
        // Create an owner with the email "john.doe@mail.com"
        ownerService.createOwner("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        // Send a PUT request to update the owner's password
        ResponseEntity<OwnerDto> responseEntity = restTemplate.exchange(
                "/owner/john.doe@mail.com/?oldPassword=securePassword123&newPassword=newSecurePassword456",
                org.springframework.http.HttpMethod.PUT,
                null,
                OwnerDto.class
        );

        // Assert that the response is OK (200) and the owner's email is unchanged
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        OwnerDto updatedOwner = responseEntity.getBody();
        assertNotNull(updatedOwner);
        assertEquals("john.doe@mail.com", updatedOwner.getEmail());
    }

    // Test updating an owner's password with an incorrect old password
    @Test
    public void testUpdateOwnerPasswordWithIncorrectOldPassword() {
        // Create an owner with the email "john.doe@mail.com"
        ownerService.createOwner("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        // Send a PUT request with an incorrect old password
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/owner/john.doe@mail.com/?oldPassword=wrongOldPassword&newPassword=newPassword456",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        // Assert the response is BAD_REQUEST (400) with the correct error message
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Incorrect old password!", responseEntity.getBody());
    }

    // Test updating an owner's password with an empty new password
    @Test
    public void testUpdateOwnerPasswordWithEmptyNewPassword() {
        // Create an owner with the email "john.doe@mail.com"
        ownerService.createOwner("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        // Send a PUT request with an empty new password
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/owner/john.doe@mail.com/?oldPassword=securePassword123&newPassword=",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        // Assert the response is BAD_REQUEST (400) with the correct error message
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The new password cannot be empty!", responseEntity.getBody());
    }

    // Test deleting an owner
    @Test
    public void testDeleteOwner() {
        // Create an owner with the email "john.doe@mail.com"
        ownerService.createOwner("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        // Send a DELETE request to remove the owner by email
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/owner/john.doe@mail.com", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        // Assert that the deletion was successful
        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Owner with email john.doe@mail.com deleted successfully.", responseEntity.getBody());

        // Verify that the owner has been deleted by trying to retrieve them
        assertThrows(GameStoreException.class, () -> ownerService.getOwner("john.doe@mail.com"));
    }

    // Test deleting a non-existing owner

    @Test
    public void testDeleteNonExistingOwner() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/owner/non.existent@mail.com", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Owner with that email does not exist!", responseEntity.getBody());
    }
}