package ca.mcgill.ecse321.gamestore.integration;

import ca.mcgill.ecse321.gamestore.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.gamestore.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.gamestore.dto.OwnerDto;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.service.EmployeeService;
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
public class EmployeeIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private EmployeeService employeeService;

    // Clears the database before and after each test to ensure a clean state
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        for (Employee employee : employeeService.getAllEmployees()) {
            employeeService.deleteEmployee(employee.getEmail());
        }
    }

    // Test creating a new employee
    @Test
    public void testCreateEmployee() {
        EmployeeRequestDto employeeDto = new EmployeeRequestDto(
                "user1", "John Doe", "john.doe@mail.com", "securePassword123"
        );


        ResponseEntity<EmployeeResponseDto> responseEntity = restTemplate.postForEntity(
                "/employee", employeeDto, EmployeeResponseDto.class
        );


        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        EmployeeResponseDto createdEmployee = responseEntity.getBody();
        assertNotNull(createdEmployee);
        assertEquals("user1", createdEmployee.getUserID());
        assertEquals("John Doe", createdEmployee.getName());
        assertEquals("john.doe@mail.com", createdEmployee.getEmail());
    }

    // Test creating a employee with an invalid email format
    @Test
    public void testCreateEmployeeWithInvalidEmail() {
        EmployeeRequestDto employeeDto = new EmployeeRequestDto(
                "user2", "Invalid Email User", "invalid-email", "password123"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/customers", employeeDto, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The email is invalid!", responseEntity.getBody());
    }

    // Test creating a employee with an existing email
    @Test
    public void testCreateEmployeeWithExistingEmail() {
        employeeService.createEmployee("user3", "Existing User", "existing@mail.com", "password123");
        EmployeeRequestDto employeeDto = new EmployeeRequestDto(
                "user4", "Duplicate Email User", "existing@mail.com", "password456"
        );

        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                "/employee", employeeDto, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertEquals("User with that email already exists!", responseEntity.getBody());
    }

    // Test retrieving a employee by email
    @Test
    public void testGetEmployeeByEmail() {
        employeeService.createEmployee("user2", "Jane Smith", "jane.smith@mail.com", "anotherPassword123");

        ResponseEntity<EmployeeResponseDto> responseEntity = restTemplate.getForEntity(
                "/employee/jane.smith@mail.com", EmployeeResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        EmployeeResponseDto retrievedEmployee = responseEntity.getBody();
        assertNotNull(retrievedEmployee);
        assertEquals("user2", retrievedEmployee.getUserID());
        assertEquals("Jane Smith", retrievedEmployee.getName());
        assertEquals("jane.smith@mail.com", retrievedEmployee.getEmail());
    }

    // Test retrieving a non-existing employee by email
    @Test
    public void testGetNonExistingEmployeeByEmail() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/employee/non.existent@mail.com", String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Employee Not Found", responseEntity.getBody()); // Ensure message matches expected case
    }

    // Test updating a employee's password
    @Test
    public void testUpdateEmployeePassword() {
        employeeService.createEmployee("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<EmployeeResponseDto> responseEntity = restTemplate.exchange(
                "/employee/john.doe@mail.com/?oldPassword=securePassword123&newPassword=newSecurePassword456",
                org.springframework.http.HttpMethod.PUT,
                null,
                EmployeeResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        EmployeeResponseDto updatedEmployee = responseEntity.getBody();
        assertNotNull(updatedEmployee);
        assertEquals("john.doe@mail.com", updatedEmployee.getEmail());
    }

    // Test updating a employee's password with an incorrect old password
    @Test
    public void testUpdateEmployeePasswordWithIncorrectOldPassword() {
        employeeService.createEmployee("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/employee/john.doe@mail.com/?oldPassword=wrongOldPassword&newPassword=newPassword456",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Incorrect old password!", responseEntity.getBody());
    }

    // Test updating a employee's password with an empty new password
    @Test
    public void testUpdateEmployeePasswordWithEmptyNewPassword() {
        employeeService.createEmployee("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/employee/john.doe@mail.com/?oldPassword=securePassword123&newPassword=",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The new password cannot be empty!", responseEntity.getBody());
    }

    // Test deleting a employee
    @Test
    public void testDeleteEmployee() {
        employeeService.createEmployee("user1", "John Doe", "john.doe@mail.com", "securePassword123");

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/employee/john.doe@mail.com", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Employee with email john.doe@mail.com deleted successfully.", responseEntity.getBody());

        // Expect GameStoreException instead of IllegalArgumentException
        assertThrows(GameStoreException.class, () -> employeeService.getEmployee("john.doe@mail.com"));
    }

    // Test deleting a non-existing employee
    @Test
    public void testDeleteNonExistingEmployee() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/employee/non.existent@mail.com", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Employee with that email does not exist!", responseEntity.getBody());
    }
}

