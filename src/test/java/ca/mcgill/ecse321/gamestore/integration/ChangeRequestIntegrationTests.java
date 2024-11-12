package ca.mcgill.ecse321.gamestore.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dto.ChangeRequestRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ChangeRequestResponseDto;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.dto.ChangeRequestListDto;
import ca.mcgill.ecse321.gamestore.service.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ChangeRequestIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    // get all services needed to setup test database
    @Autowired
    private ChangeRequestService changeRequestService;
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private OwnerService ownerService;

    // define test values as constants
    private final String TEST_EMPLOYEE_EMAIL = "test.employee@email.com";
    private final String TEST_MANAGER_EMAIL = "owner@email.com";

    // set up test data before each test
    @BeforeEach
    public void setupTestData() {
        // create a test employee
        employeeService.createEmployee(
            "testemployee", 
            "Test Employee",
            TEST_EMPLOYEE_EMAIL,
            "password123"
        );

        // create a test owner/manager
        ownerService.createOwner(
            "manager1",
            "Test Manager",
            TEST_MANAGER_EMAIL,
            "password123"
        );
    }

    // clean up database after each test
    @AfterEach
    public void clearDatabase() {
        // delete change requests first (foreign key constraint)
        for (ChangeRequest request : changeRequestService.getAllChangeRequests()) {
            changeRequestService.deleteChangeRequest(request.getId(), TEST_MANAGER_EMAIL);
        }


        // delete test data
        employeeService.deleteEmployee(TEST_EMPLOYEE_EMAIL);
        ownerService.deleteOwner(TEST_MANAGER_EMAIL);
    }

    // create change request with valid input
    @Test
    public void testCreateChangeRequest_Success() {
        // setup request with valid data
        ChangeRequestRequestDto request = createChangeRequestRequest(TEST_EMPLOYEE_EMAIL);

        // send POST request to create change request
        ResponseEntity<ChangeRequestResponseDto> response = client.postForEntity(
            "/change-requests",
            request,
            ChangeRequestResponseDto.class
        );

        // check that the request was created properly
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ChangeRequestResponseDto created = response.getBody();
        assertNotNull(created);
        assertNotNull(created.getId());
        assertEquals(TEST_EMPLOYEE_EMAIL, created.getRequestCreatorEmail());
        assertEquals("InProgress", created.getStatus());
    }

    // create change request with null email
    @Test
    public void testCreateChangeRequest_NullEmail() {
        // setup request with null email
        ChangeRequestRequestDto request = new ChangeRequestRequestDto();

        // send POST request
        ResponseEntity<String> response = client.postForEntity(
            "/change-requests",
            request,
            String.class
        );

        // check error response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Request creator email cannot be empty.", response.getBody());
    }

    // create change request with non-existent employee
    @Test
    public void testCreateChangeRequest_NonExistentEmployee() {
        // setup request with non-existent employee
        ChangeRequestRequestDto request = createChangeRequestRequest("nonexistent@email.com");

        // send POST request
        ResponseEntity<String> response = client.postForEntity(
            "/change-requests",
            request,
            String.class
        );

        // check error response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Employee not found with email: nonexistent@email.com", response.getBody());
    }

    // approve change request successfully
    @Test
    public void testApproveChangeRequest_Success() {
        // first create a change request
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        // approve the request
        ResponseEntity<ChangeRequestResponseDto> response = client.exchange(
            "/change-requests/{id}/status?managerEmail={email}&status={status}",
            org.springframework.http.HttpMethod.PUT,
            null,
            ChangeRequestResponseDto.class,
            requestId,
            TEST_MANAGER_EMAIL,
            "APPROVED"
        );

        // check that the request was approved
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ChangeRequestResponseDto approved = response.getBody();
        assertNotNull(approved);
        assertEquals(requestId, approved.getId());
        assertEquals("Approved", approved.getStatus());
    }

    // try to approve as non-manager
    @Test
    public void testApproveChangeRequest_NotManager() {
        // first create a change request
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        // try to approve with non-manager email
        ResponseEntity<String> response = client.exchange(    
            "/change-requests/{id}/status?managerEmail={email}&status={status}",
            org.springframework.http.HttpMethod.PUT,
            null,
            String.class,                           
            requestId,
            "notmanager@email.com",
            "APPROVED"
        );

        // check error response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Only the manager can approve requests.", response.getBody());
    }

    // try to approve already approved request
    @Test
    public void testApproveChangeRequest_AlreadyProcessed() {
        // first create and approve a change request
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        client.exchange(
            "/change-requests/{id}/status?managerEmail={email}&status={status}",
            org.springframework.http.HttpMethod.PUT,
            null,
            ChangeRequestResponseDto.class,
            requestId,
            TEST_MANAGER_EMAIL,
            "APPROVED"
        );

        // try to approve again
        ResponseEntity<String> response = client.exchange(
            "/change-requests/{id}/status?managerEmail={email}&status={status}",
            org.springframework.http.HttpMethod.PUT,
            null,
            String.class,
            requestId,
            TEST_MANAGER_EMAIL,
            "APPROVED"
        );

        // check error response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Only InProgress requests can be approved.", response.getBody());
    }

    // decline change request successfully
    @Test
    public void testDeclineChangeRequest_Success() {
        // first create a change request
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        // decline the request
        ResponseEntity<ChangeRequestResponseDto> response = client.exchange(
            "/change-requests/{id}/status?managerEmail={email}&status={status}",
            org.springframework.http.HttpMethod.PUT,
            null,
            ChangeRequestResponseDto.class,
            requestId,
            TEST_MANAGER_EMAIL,
            "DECLINED"
        );

        // check that the request was declined
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ChangeRequestResponseDto declined = response.getBody();
        assertNotNull(declined);
        assertEquals(requestId, declined.getId());
        assertEquals("Declined", declined.getStatus());
    }

    // try to decline as non-manager
    @Test
    public void testDeclineChangeRequest_NotManager() {
        // first create a change request
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        // try to decline with non-manager email
        ResponseEntity<String> response = client.exchange(    
            "/change-requests/{id}/status?managerEmail={email}&status={status}",
            org.springframework.http.HttpMethod.PUT,
            null,
            String.class,                           
            requestId,
            "notmanager@email.com",
            "DECLINED"
        );

        // check error response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Only the manager can decline requests.", response.getBody());
    }

    //test updating the chnage request w/ invalid status
    @Test
    public void testUpdateStatus_InvalidStatus() {
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        ResponseEntity<String> response = client.exchange(
            "/change-requests/{id}/status?managerEmail={email}&status={status}",
            org.springframework.http.HttpMethod.PUT,
            null,
            String.class,
            requestId,
            TEST_MANAGER_EMAIL,
            "INVALID_STATUS"
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().contains("Invalid status. Must be either APPROVED or DECLINED"));
    }

    // get change request by id successfully
    @Test
    public void testGetChangeRequestById_Success() {
        // first create a change request
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        // get the request by id
        ResponseEntity<ChangeRequestResponseDto> response = client.getForEntity(
            "/change-requests/{id}",
            ChangeRequestResponseDto.class,
            requestId
        );

        // check the retrieved request
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ChangeRequestResponseDto retrieved = response.getBody();
        assertNotNull(retrieved);
        assertEquals(requestId, retrieved.getId());
        assertEquals(TEST_EMPLOYEE_EMAIL, retrieved.getRequestCreatorEmail());
        assertEquals("InProgress", retrieved.getStatus());
    }

    // get non-existent change request
    @Test
    public void testGetChangeRequestById_NonExistent() {
        // try to get non-existent request
        ResponseEntity<String> response = client.getForEntity(
            "/change-requests/999",
            String.class
        );

        // check error response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("ChangeRequest not found with ID: 999", response.getBody());
    }

    // get all change requests
    @Test
    public void testGetAllChangeRequests() {
        // create two test change requests
        createTestChangeRequest();
        createTestChangeRequest();

        // get all requests
        ResponseEntity<ChangeRequestListDto> response = client.getForEntity(
            "/change-requests",
            ChangeRequestListDto.class
        );

        // check that we got both requests
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ChangeRequestListDto requests = response.getBody();
        assertNotNull(requests);
        assertEquals(2, requests.getChangeRequests().size());
    }

    // get all change requests when none exist
    @Test
    public void testGetAllChangeRequests_NoRequests() {
        // get all requests without creating any
        ResponseEntity<ChangeRequestListDto> response = client.getForEntity(
            "/change-requests",
            ChangeRequestListDto.class
        );

        // check response - should be OK with empty list
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ChangeRequestListDto requests = response.getBody();
        assertNotNull(requests);
        assertTrue(requests.getChangeRequests().isEmpty());
    }

    // delete change request successfully
    @Test
    public void testDeleteChangeRequest_Success() {
        // first create a change request
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        // delete the request
        ResponseEntity<String> response = client.exchange(
            "/change-requests/{id}?managerEmail={email}",
            org.springframework.http.HttpMethod.DELETE,
            null,
            String.class,
            requestId,
            TEST_MANAGER_EMAIL
        );

        // check deletion was successful
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("ChangeRequest with ID " + requestId + " deleted successfully.", response.getBody());
        
        // verify request was deleted by trying to get it
        ResponseEntity<String> getResponse = client.getForEntity(
            "/change-requests/{id}",
            String.class,
            requestId
        );
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    // try deleting as a non-manager
    @Test
    public void testDeleteChangeRequest_NotManager() {
        ResponseEntity<ChangeRequestResponseDto> createResponse = createTestChangeRequest();
        Integer requestId = createResponse.getBody().getId();

        ResponseEntity<String> response = client.exchange(    
            "/change-requests/{id}?managerEmail={email}",
            org.springframework.http.HttpMethod.DELETE,
            null,
            String.class,                            
            requestId,
            "notmanager@email.com"
        );

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Only the manager can delete requests.", response.getBody());
    }


    // helper method to create a change request request
    private ChangeRequestRequestDto createChangeRequestRequest(String email) {
        ChangeRequestRequestDto request = new ChangeRequestRequestDto();
        request.setRequestCreatorEmail(email);
        return request;
    }

    // helper method to create a test change request
    private ResponseEntity<ChangeRequestResponseDto> createTestChangeRequest() {
        return client.postForEntity(
            "/change-requests",
            createChangeRequestRequest(TEST_EMPLOYEE_EMAIL),
            ChangeRequestResponseDto.class
        );
    }
}