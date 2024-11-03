package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import java.util.Date;

public class ChangeRequestResponseDto {
    private Integer id;
    private Date timeRequest;
    private String status; // Enum as String: "Approved", "Declined", "InProgress"
    private String requestCreatorEmail; // Email of the Employee (requestCreator)

    // Default constructor
    public ChangeRequestResponseDto() {
    }

    // Constructor to create DTO from model
    public ChangeRequestResponseDto(ChangeRequest model) {
        this.id = model.getId();
        this.timeRequest = model.getTimeRequest();
        this.status = model.getStatus().toString();
        this.requestCreatorEmail = model.getRequestCreator().getEmail();
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getTimeRequest() {
        return timeRequest;
    }

    public void setTimeRequest(Date timeRequest) {
        this.timeRequest = timeRequest;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequestCreatorEmail() {
        return requestCreatorEmail;
    }

    public void setRequestCreatorEmail(String requestCreatorEmail) {
        this.requestCreatorEmail = requestCreatorEmail;
    }
}
