package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class ChangeRequestListDto {
    private List<ChangeRequestResponseDto> changeRequests;

    // Default constructor
    public ChangeRequestListDto() {
    }

    // Constructor with change requests
    public ChangeRequestListDto(List<ChangeRequestResponseDto> changeRequests) {
        this.changeRequests = changeRequests;
    }

    // Getter and setter
    public List<ChangeRequestResponseDto> getChangeRequests() {
        return changeRequests;
    }

    public void setChangeRequests(List<ChangeRequestResponseDto> changeRequests) {
        this.changeRequests = changeRequests;
    }
}