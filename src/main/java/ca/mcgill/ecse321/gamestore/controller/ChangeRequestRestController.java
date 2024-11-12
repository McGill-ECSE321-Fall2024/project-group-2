package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.ChangeRequestRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ChangeRequestResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ChangeRequestListDto;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.service.ChangeRequestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    // approve change request, return response dto
    @PutMapping("/{id}/approve")
    public ChangeRequestResponseDto approveChangeRequest(@PathVariable Integer id, @RequestParam String managerEmail) {
        ChangeRequest changeRequest = changeRequestService.approveChangeRequest(id, managerEmail);
        return new ChangeRequestResponseDto(changeRequest);
    }

    // decline change request, return response dto
    @PutMapping("/{id}/decline")
    public ChangeRequestResponseDto declineChangeRequest(@PathVariable Integer id, @RequestParam String managerEmail) {
        ChangeRequest changeRequest = changeRequestService.declineChangeRequest(id, managerEmail);
        return new ChangeRequestResponseDto(changeRequest);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteChangeRequest(@PathVariable Integer id, @RequestParam String managerEmail) {
        changeRequestService.deleteChangeRequest(id, managerEmail);
        return "ChangeRequest with ID " + id + " deleted successfully.";
    }
}