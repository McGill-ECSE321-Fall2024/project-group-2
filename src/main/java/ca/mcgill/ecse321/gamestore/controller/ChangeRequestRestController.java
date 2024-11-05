package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.ChangeRequestRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ChangeRequestResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ChangeRequestListDto;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.service.ChangeRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/change-requests")
public class ChangeRequestRestController {

    @Autowired
    private ChangeRequestService changeRequestService;

    /**
     * Creates a new ChangeRequest.
     * @param dto ChangeRequest data from the request body.
     * @return The created ChangeRequest as a DTO.
     */
    @PostMapping
    public ChangeRequestResponseDto createChangeRequest(@RequestBody ChangeRequestRequestDto dto) {
        ChangeRequest changeRequest = changeRequestService.createChangeRequest(dto);
        return new ChangeRequestResponseDto(changeRequest);
    }

    /**
     * Retrieves a ChangeRequest by its ID.
     * @param id The ID of the change request.
     * @return The ChangeRequest as a DTO.
     */
    @GetMapping("/{id}")
    public ChangeRequestResponseDto getChangeRequestById(@PathVariable Integer id) {
        ChangeRequest changeRequest = changeRequestService.getChangeRequestById(id);
        return new ChangeRequestResponseDto(changeRequest);
    }

    /**
     * Retrieves all ChangeRequests.
     * @return A list of ChangeRequest DTOs.
     */
    @GetMapping
    public ChangeRequestListDto getAllChangeRequests() {
        List<ChangeRequest> changeRequests = changeRequestService.getAllChangeRequests();
        List<ChangeRequestResponseDto> changeRequestDtos = new ArrayList<>();
        for (ChangeRequest changeRequest : changeRequests) {
            changeRequestDtos.add(new ChangeRequestResponseDto(changeRequest));
        }
        return new ChangeRequestListDto(changeRequestDtos);
    }

    /**
     * Approves a ChangeRequest.
     * @param id The ID of the change request to approve.
     * @return The updated ChangeRequest as a DTO.
     */
    @PutMapping("/{id}/approve")
    public ChangeRequestResponseDto approveChangeRequest(@PathVariable Integer id) {
        ChangeRequest changeRequest = changeRequestService.approveChangeRequest(id);
        return new ChangeRequestResponseDto(changeRequest);
    }

    /**
     * Declines a ChangeRequest.
     * @param id The ID of the change request to decline.
     * @return The updated ChangeRequest as a DTO.
     */
    @PutMapping("/{id}/decline")
    public ChangeRequestResponseDto declineChangeRequest(@PathVariable Integer id) {
        ChangeRequest changeRequest = changeRequestService.declineChangeRequest(id);
        return new ChangeRequestResponseDto(changeRequest);
    }
}