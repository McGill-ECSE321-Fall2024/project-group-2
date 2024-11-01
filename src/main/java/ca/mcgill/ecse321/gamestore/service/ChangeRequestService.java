package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Iterator;

import ca.mcgill.ecse321.gamestore.dto.ChangeRequestDto;
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
    public ChangeRequest createChangeRequest(ChangeRequestDto dto) {
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

        // Create new ChangeRequest using the model's constructor
        ChangeRequest changeRequest = new ChangeRequest();

        // Set current time as java.sql.Date
        Date sqlDate = new Date(System.currentTimeMillis());
        changeRequest.setTimeRequest(sqlDate);

        changeRequest.setStatus(RequestStatus.InProgress); // Default status
        changeRequest.setRequestCreator(creator);
        changeRequest.setRequestManager(manager);

        // Save to repository
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
        ChangeRequest request = getChangeRequestById(requestId);
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
        ChangeRequest request = getChangeRequestById(requestId);
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
        Optional<ChangeRequest> requestOpt = changeRequestRepository.findById(requestId);
        if (!requestOpt.isPresent()) {
            throw new IllegalArgumentException("ChangeRequest not found with ID: " + requestId);
        }
        return requestOpt.get();
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
     * Helper method to get the single Owner.
     * Avoids using streams for simplicity.
     * 
     * @return The single Owner in the system.
     */
    private Owner getSingleOwner() {
        Iterable<Owner> iterable = ownerRepository.findAll();
        Iterator<Owner> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        } else {
            return null;
        }
    }
    
}

