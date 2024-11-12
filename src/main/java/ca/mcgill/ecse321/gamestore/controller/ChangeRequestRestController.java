package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.ChangeRequestRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ChangeRequestResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ChangeRequestListDto;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.service.ChangeRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/change-requests")
public class ChangeRequestRestController {

    @Autowired
    private ChangeRequestService changeRequestService;

    // creates change request using request dto, returns repsonse dto
    @PostMapping
    public ChangeRequestResponseDto createChangeRequest(@RequestBody ChangeRequestRequestDto dto) {
        ChangeRequest changeRequest = changeRequestService.createChangeRequest(dto);
        return new ChangeRequestResponseDto(changeRequest);
    }

    // gets chnage request by id, returns response dto
    @GetMapping("/{id}")
    public ChangeRequestResponseDto getChangeRequestById(@PathVariable Integer id) {
        ChangeRequest changeRequest = changeRequestService.getChangeRequestById(id);
        return new ChangeRequestResponseDto(changeRequest);
    }

    // gets all change requests, returns list dto
    @GetMapping
    public ChangeRequestListDto getAllChangeRequests() {
        List<ChangeRequest> changeRequests = changeRequestService.getAllChangeRequests();
        List<ChangeRequestResponseDto> changeRequestDtos = new ArrayList<>();
        for (ChangeRequest changeRequest : changeRequests) {
            changeRequestDtos.add(new ChangeRequestResponseDto(changeRequest));
        }
        return new ChangeRequestListDto(changeRequestDtos);
    }

    // update change request status (approve/decline), return response dto
    @PutMapping("/{id}/status")
    public ChangeRequestResponseDto updateChangeRequestStatus(
        @PathVariable Integer id, 
        @RequestParam String managerEmail,
        @RequestParam String status) {
        status = status.toUpperCase();  
        if (!status.equals("APPROVED") && !status.equals("DECLINED")) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                "Invalid status. Must be either APPROVED or DECLINED"
            );
        }
        
        if (status.equals("APPROVED")) {
            return new ChangeRequestResponseDto(
                changeRequestService.approveChangeRequest(id, managerEmail)
            );
        } else {  // must be DECLINED since we checked above
            return new ChangeRequestResponseDto(
                changeRequestService.declineChangeRequest(id, managerEmail)
            );
        }
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteChangeRequest(@PathVariable Integer id, @RequestParam String managerEmail) {
        changeRequestService.deleteChangeRequest(id, managerEmail);
        return "ChangeRequest with ID " + id + " deleted successfully.";
    }
}