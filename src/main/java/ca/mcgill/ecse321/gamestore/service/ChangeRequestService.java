package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse321.gamestore.dto.ChangeRequestRequestDto;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;
import ca.mcgill.ecse321.gamestore.model.Employee;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.repository.ChangeRequestRepository;
import ca.mcgill.ecse321.gamestore.repository.EmployeeRepository;
import ca.mcgill.ecse321.gamestore.repository.OwnerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    // creates change request w/ status set to in progress
    @Transactional
    public ChangeRequest createChangeRequest(ChangeRequestRequestDto dto) {
        if (dto.getRequestCreatorEmail() == null || dto.getRequestCreatorEmail().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Request creator email cannot be empty.");
        }

        // fetch associated employee (requestCreator)
        Employee creator = employeeRepository.findEmployeeByEmail(dto.getRequestCreatorEmail());
        if (creator == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, 
                "Employee not found with email: " + dto.getRequestCreatorEmail());
        }

        // fetch the owner (requestManager) using repository method
        Owner manager = ownerRepository.findOwnerByEmail("owner@email.com");
        if (manager == null) {
            throw new GameStoreException(HttpStatus.INTERNAL_SERVER_ERROR, 
                "No owner found in the system.");
        }

        // create new change request
        ChangeRequest changeRequest = new ChangeRequest();
        changeRequest.setTimeRequest(new Date(System.currentTimeMillis()));
        changeRequest.setStatus(RequestStatus.InProgress); // all requests start as InProgress
        changeRequest.setRequestCreator(creator);
        changeRequest.setRequestManager(manager);

        return changeRequestRepository.save(changeRequest);
    }

    // approves change request
    @Transactional
    public ChangeRequest approveChangeRequest(int requestId, String managerEmail) {
        // verify manager
        Owner manager = ownerRepository.findOwnerByEmail(managerEmail);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, 
                "Only the manager can approve requests.");
        }

        ChangeRequest request = changeRequestRepository.findChangeRequestById(requestId);
        if (request == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, 
                "ChangeRequest not found with ID: " + requestId);
        }
        if (request.getStatus() != RequestStatus.InProgress) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, 
                "Only InProgress requests can be approved.");
        }
        request.setStatus(RequestStatus.Approved);
        return changeRequestRepository.save(request);
    }

    // declines a change request, must be in progress
    @Transactional
    public ChangeRequest declineChangeRequest(int requestId, String managerEmail) {
        // verify manager
        Owner manager = ownerRepository.findOwnerByEmail(managerEmail);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, 
                "Only the manager can decline requests.");
        }

        ChangeRequest request = changeRequestRepository.findChangeRequestById(requestId);
        if (request == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, 
                "ChangeRequest not found with ID: " + requestId);
        }
        if (request.getStatus() != RequestStatus.InProgress) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, 
                "Only InProgress requests can be declined.");
        }
        request.setStatus(RequestStatus.Declined);
        return changeRequestRepository.save(request);
    }

    // gets change request by id
    @Transactional
    public ChangeRequest getChangeRequestById(int requestId) {
        ChangeRequest request = changeRequestRepository.findChangeRequestById(requestId);
        if (request == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, 
                "ChangeRequest not found with ID: " + requestId);
        }
        return request;
    }

    // gets all change requests
    @Transactional
    public List<ChangeRequest> getAllChangeRequests() {
        Iterable<ChangeRequest> iterable = changeRequestRepository.findAll();
        List<ChangeRequest> list = new ArrayList<>();
        for (ChangeRequest changeRequest : iterable) {
            list.add(changeRequest);
        }
        return list;
    }

    // deletes a change request, can only be done by manager
    @Transactional
    public void deleteChangeRequest(int requestId, String managerEmail) {
        // verify manager
        Owner manager = ownerRepository.findOwnerByEmail(managerEmail);
        if (manager == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, 
                "Only the manager can delete requests.");
        }

        ChangeRequest request = changeRequestRepository.findChangeRequestById(requestId);
        if (request == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, 
                "ChangeRequest not found with ID: " + requestId);
        }
        changeRequestRepository.deleteById(requestId);
    }
}