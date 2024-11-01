package ca.mcgill.ecse321.gamestore.controller;

import java.util.List;
import java.util.stream.Collectors;

import ca.mcgill.ecse321.gamestore.dto.ChangeRequestDto;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.service.ChangeRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/changerequests")
public class ChangeRequestController {

    @Autowired
    private ChangeRequestService changeRequestService;

    /**
     * Creates a new ChangeRequest.
     * 
     * @param dto The ChangeRequest data.
     * @return The created ChangeRequest DTO.
     */
    @PostMapping
    public ChangeRequestDto createChangeRequest(@RequestBody ChangeRequestDto dto) {
        ChangeRequest changeRequest = changeRequestService.createChangeRequest(dto);
        return new ChangeRequestDto(changeRequest);
    }

    /**
     * Approves a ChangeRequest.
     * 
     * @param requestId The ID of the request.
     * @return The updated ChangeRequest DTO.
     */
    @PutMapping("/{id}/approve")
    public ChangeRequestDto approveChangeRequest(@PathVariable("id") int requestId) {
        ChangeRequest changeRequest = changeRequestService.approveChangeRequest(requestId);
        return new ChangeRequestDto(changeRequest);
    }

    /**
     * Declines a ChangeRequest.
     * 
     * @param requestId The ID of the request.
     * @return The updated ChangeRequest DTO.
     */
    @PutMapping("/{id}/decline")
    public ChangeRequestDto declineChangeRequest(@PathVariable("id") int requestId) {
        ChangeRequest changeRequest = changeRequestService.declineChangeRequest(requestId);
        return new ChangeRequestDto(changeRequest);
    }

    /**
     * Retrieves a ChangeRequest by ID.
     * 
     * @param requestId The ID of the request.
     * @return The ChangeRequest DTO.
     */
    @GetMapping("/{id}")
    public ChangeRequestDto getChangeRequestById(@PathVariable("id") int requestId) {
        ChangeRequest changeRequest = changeRequestService.getChangeRequestById(requestId);
        return new ChangeRequestDto(changeRequest);
    }

    /**
     * Retrieves all ChangeRequests.
     * 
     * @return List of ChangeRequest DTOs.
     */
    @GetMapping
    public List<ChangeRequestDto> getAllChangeRequests() {
        List<ChangeRequest> changeRequests = changeRequestService.getAllChangeRequests();
        return changeRequests.stream()
                .map(ChangeRequestDto::new)
                .collect(Collectors.toList());
    }
}

