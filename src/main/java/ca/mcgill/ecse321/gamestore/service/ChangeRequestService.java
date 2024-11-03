package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import ca.mcgill.ecse321.gamestore.dto.ChangeRequestRequestDto;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;
import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.repository.ChangeRequestRepository;
import ca.mcgill.ecse321.gamestore.repository.EmployeeRepository;
import ca.mcgill.ecse321.gamestore.repository.OwnerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangeRequestService {

    @Autowired
    private ChangeRequestRepository changeRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    /**
     * Creates a new ChangeRequest.
     * 
     * @param dto Data Transfer Object containing request details.
     * @return The created ChangeRequest.
     */
    @Transactional
    public ChangeRequest createChangeRequest(ChangeRequestRequestDto dto) {
        // Validate inputs
        if (dto.getRequestCreatorEmail() == null || dto.getRequestCreatorEmail().isEmpty()) {
            throw new IllegalArgumentException("Request creator email cannot be empty.");
        }

        // Fetch associated Employee (requestCreator)
        Employee creator = employeeRepository.findEmployeeByEmail(dto.getRequestCreatorEmail());
        if (creator == null) {
            throw new IllegalArgumentException("Employee not found with email: " + dto.getRequestCreatorEmail());
        }

        // Fetch the single Owner (requestManager)
        Owner manager = getSingleOwner();
        if (manager == null) {
            throw new IllegalStateException("No owner found in the system.");
        }

        // Create new ChangeRequest
        ChangeRequest changeRequest = new ChangeRequest();
        
        // Set current time
        Date sqlDate = new Date(System.currentTimeMillis());
        changeRequest.setTimeRequest(sqlDate);

        // Set status from dto if provided, otherwise default to InProgress
        if (dto.getStatus() != null && !dto.getStatus().isEmpty()) {
            try {
                changeRequest.setStatus(RequestStatus.valueOf(dto.getStatus()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Invalid status: " + dto.getStatus());
            }
        } else {
            changeRequest.setStatus(RequestStatus.InProgress);
        }

        changeRequest.setRequestCreator(creator);
        changeRequest.setRequestManager(manager);

        return changeRequestRepository.save(changeRequest);
    }

    /**
     * Approves a ChangeRequest.
     * 
     * @param requestId ID of the request to approve.
     * @return The updated ChangeRequest.
     */
    @Transactional
    public ChangeRequest approveChangeRequest(int requestId) {
        ChangeRequest request = changeRequestRepository.findChangeRequestById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("ChangeRequest not found with ID: " + requestId);
        }
        request.setStatus(RequestStatus.Approved);
        return changeRequestRepository.save(request);
    }

    /**
     * Declines a ChangeRequest.
     * 
     * @param requestId ID of the request to decline.
     * @return The updated ChangeRequest.
     */
    @Transactional
    public ChangeRequest declineChangeRequest(int requestId) {
        ChangeRequest request = changeRequestRepository.findChangeRequestById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("ChangeRequest not found with ID: " + requestId);
        }
        request.setStatus(RequestStatus.Declined);
        return changeRequestRepository.save(request);
    }

    /**
     * Retrieves a ChangeRequest by its ID.
     * 
     * @param requestId The ID of the request.
     * @return The ChangeRequest.
     */
    @Transactional(readOnly = true)
    public ChangeRequest getChangeRequestById(int requestId) {
        ChangeRequest request = changeRequestRepository.findChangeRequestById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("ChangeRequest not found with ID: " + requestId);
        }
        return request;
    }

    /**
     * Retrieves all ChangeRequests.
     * 
     * @return List of ChangeRequests.
     */
    @Transactional(readOnly = true)
    public List<ChangeRequest> getAllChangeRequests() {
        Iterable<ChangeRequest> iterable = changeRequestRepository.findAll();
        List<ChangeRequest> list = new ArrayList<>();
        for (ChangeRequest changeRequest : iterable) {
            list.add(changeRequest);
        }
        return list;
    }

    /**
     * Helper method to find the Owner.
     * 
     * @return The Owner in the system.
     */
    private Owner getSingleOwner() {
        Iterable<Owner> owners = ownerRepository.findAll();
        for (Owner owner : owners) {
            return owner;  // Returns the first owner found
        }
        return null;  // Returns null if no owners found
    }
}