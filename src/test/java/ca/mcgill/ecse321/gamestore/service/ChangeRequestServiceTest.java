package ca.mcgill.ecse321.gamestore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.dto.ChangeRequestRequestDto;
import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;
import ca.mcgill.ecse321.gamestore.repository.ChangeRequestRepository;
import ca.mcgill.ecse321.gamestore.repository.EmployeeRepository;
import ca.mcgill.ecse321.gamestore.repository.OwnerRepository;

@SpringBootTest
public class ChangeRequestServiceTest {

    @Mock
    private ChangeRequestRepository changeRequestRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private OwnerRepository ownerRepository;

    @InjectMocks
    private ChangeRequestService changeRequestService;

    // tests creation of change request with all valid fields
    @Test
    public void testCreateValidChangeRequest() {
        // Arrange
        String email = "employee@email.com";
        
        // Create test employee and owner
        Employee employee = new Employee("emp1", "Test Employee", email, "password");
        Owner owner = new Owner("own1", "Test Owner", "owner@email.com", "password");
        
        // Mock repository behaviors for successful path
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(employee);
        when(ownerRepository.findOwnerByEmail("owner@email.com")).thenReturn(owner);
        when(changeRequestRepository.save(any(ChangeRequest.class))).thenAnswer(i -> i.getArgument(0));

        // Create DTO with required fields
        ChangeRequestRequestDto dto = new ChangeRequestRequestDto();
        dto.setRequestCreatorEmail(email);

        // Act
        ChangeRequest createdRequest = changeRequestService.createChangeRequest(dto);

        // Assert
        assertNotNull(createdRequest);
        assertEquals(RequestStatus.InProgress, createdRequest.getStatus());
        assertEquals(email, createdRequest.getRequestCreator().getEmail());
        assertNotNull(createdRequest.getTimeRequest());
        verify(changeRequestRepository).save(any(ChangeRequest.class));
        verify(employeeRepository).findEmployeeByEmail(email);
        verify(ownerRepository).findOwnerByEmail("owner@email.com");
    }

    // should throw error when email is missing
    @Test
    public void testCreateChangeRequest_NullEmail() {
        // Arrange: Create request with missing email
        ChangeRequestRequestDto dto = new ChangeRequestRequestDto();

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> changeRequestService.createChangeRequest(dto));
        assertEquals("Request creator email cannot be empty.", e.getMessage());
    }

    // should throw error when there's no employee
    @Test
    public void testCreateChangeRequest_EmployeeNotFound() {
        // Arrange: Set up request with non-existent employee
        String email = "nonexistent@email.com";
        ChangeRequestRequestDto dto = new ChangeRequestRequestDto();
        dto.setRequestCreatorEmail(email);
        
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(null);

        // Act & Assert: Verify employee not found exception
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> changeRequestService.createChangeRequest(dto));
        assertEquals("Employee not found with email: " + email, e.getMessage());
        verify(employeeRepository).findEmployeeByEmail(email);
    }

    // should throw error when there's no owner
    @Test
    public void testCreateChangeRequest_NoOwner() {
        // Arrange: Set up request with no owner in system
        String email = "employee@email.com";
        Employee employee = new Employee("emp1", "Test Employee", email, "password");
        
        when(employeeRepository.findEmployeeByEmail(email)).thenReturn(employee);
        when(ownerRepository.findOwnerByEmail("owner@email.com")).thenReturn(null);

        ChangeRequestRequestDto dto = new ChangeRequestRequestDto();
        dto.setRequestCreatorEmail(email);

        // Act & Assert
        IllegalStateException e = assertThrows(IllegalStateException.class, 
            () -> changeRequestService.createChangeRequest(dto));
        assertEquals("No owner found in the system.", e.getMessage());
        verify(ownerRepository).findOwnerByEmail("owner@email.com");
    }

    // successful approval by manager
    @Test
    public void testApproveChangeRequest_Success() {
        // Arrange
        Integer requestId = 1;
        String managerEmail = "owner@email.com";
        
        ChangeRequest request = new ChangeRequest();
        request.setId(requestId);
        request.setStatus(RequestStatus.InProgress);
        
        Owner owner = new Owner("own1", "Test Owner", managerEmail, "password");
        
        when(ownerRepository.findOwnerByEmail(managerEmail)).thenReturn(owner);
        when(changeRequestRepository.findChangeRequestById(requestId)).thenReturn(request);
        when(changeRequestRepository.save(any(ChangeRequest.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ChangeRequest approved = changeRequestService.approveChangeRequest(requestId, managerEmail);

        // Assert
        assertNotNull(approved);
        assertEquals(RequestStatus.Approved, approved.getStatus());
        verify(ownerRepository).findOwnerByEmail(managerEmail);
        verify(changeRequestRepository).findChangeRequestById(requestId);
        verify(changeRequestRepository).save(any(ChangeRequest.class));
    }

    // try to approve already approved request
    @Test
    public void testApproveChangeRequest_AlreadyProcessed() {
        // Arrange
        Integer requestId = 1;
        String managerEmail = "owner@email.com";
        
        ChangeRequest request = new ChangeRequest();
        request.setId(requestId);
        request.setStatus(RequestStatus.Approved);
        
        Owner owner = new Owner("own1", "Test Owner", managerEmail, "password");
        
        when(ownerRepository.findOwnerByEmail(managerEmail)).thenReturn(owner);
        when(changeRequestRepository.findChangeRequestById(requestId)).thenReturn(request);

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> changeRequestService.approveChangeRequest(requestId, managerEmail));
        assertEquals("Only InProgress requests can be approved.", e.getMessage());
    }

    // try to approve with non-manager
    @Test
    public void testApproveChangeRequest_NotManager() {
        // Arrange
        Integer requestId = 1;
        String email = "notowner@email.com";
        
        when(ownerRepository.findOwnerByEmail(email)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> changeRequestService.approveChangeRequest(requestId, email));
        assertEquals("Only the manager can decline requests.", e.getMessage());
        verify(ownerRepository).findOwnerByEmail(email);
    }

    // successful decline by manager
    @Test
    public void testDeclineChangeRequest_Success() {
        // Arrange
        Integer requestId = 1;
        String managerEmail = "owner@email.com";
        
        ChangeRequest request = new ChangeRequest();
        request.setId(requestId);
        request.setStatus(RequestStatus.InProgress);
        
        Owner owner = new Owner("own1", "Test Owner", managerEmail, "password");
        
        when(ownerRepository.findOwnerByEmail(managerEmail)).thenReturn(owner);
        when(changeRequestRepository.findChangeRequestById(requestId)).thenReturn(request);
        when(changeRequestRepository.save(any(ChangeRequest.class))).thenAnswer(i -> i.getArgument(0));

        // Act
        ChangeRequest declined = changeRequestService.declineChangeRequest(requestId, managerEmail);

        // Assert
        assertNotNull(declined);
        assertEquals(RequestStatus.Declined, declined.getStatus());
        verify(ownerRepository).findOwnerByEmail(managerEmail);
        verify(changeRequestRepository).findChangeRequestById(requestId);
        verify(changeRequestRepository).save(any(ChangeRequest.class));
    }

    // get request by id
    @Test
    public void testGetChangeRequestById_Success() {
        // Arrange
        Integer requestId = 1;
        ChangeRequest request = new ChangeRequest();
        request.setId(requestId);
        request.setStatus(RequestStatus.InProgress);
        
        when(changeRequestRepository.findChangeRequestById(requestId)).thenReturn(request);

        // Act
        ChangeRequest result = changeRequestService.getChangeRequestById(requestId);

        // Assert
        assertNotNull(result);
        assertEquals(requestId, result.getId());
        assertEquals(RequestStatus.InProgress, result.getStatus());
        verify(changeRequestRepository).findChangeRequestById(requestId);
    }

    // get request by id - not found
    @Test
    public void testGetChangeRequestById_NotFound() {
        // Arrange
        Integer requestId = 999;
        when(changeRequestRepository.findChangeRequestById(requestId)).thenReturn(null);

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> changeRequestService.getChangeRequestById(requestId));
        assertEquals("ChangeRequest not found with ID: " + requestId, e.getMessage());
        verify(changeRequestRepository).findChangeRequestById(requestId);
    }

    // get all requests
    @Test
    public void testGetAllChangeRequests_Success() {
        // Arrange
        ChangeRequest request1 = new ChangeRequest();
        request1.setStatus(RequestStatus.InProgress);
        ChangeRequest request2 = new ChangeRequest();
        request2.setStatus(RequestStatus.Approved);
        
        when(changeRequestRepository.findAll()).thenReturn(List.of(request1, request2));

        // Act
        List<ChangeRequest> results = changeRequestService.getAllChangeRequests();

        // Assert
        assertNotNull(results);
        assertEquals(2, results.size());
        verify(changeRequestRepository).findAll();
    }
}
