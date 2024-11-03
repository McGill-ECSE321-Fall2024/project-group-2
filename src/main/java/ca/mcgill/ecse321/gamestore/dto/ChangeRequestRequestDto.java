package ca.mcgill.ecse321.gamestore.dto;

public class ChangeRequestRequestDto {
    private String requestCreatorEmail; // Email of the Employee (requestCreator)
    private String status; // Enum as String: "Approved", "Declined", "InProgress"
    
    // Default constructor
    public ChangeRequestRequestDto() {
    }
    
    // Getters and Setters
    public String getRequestCreatorEmail() {
        return requestCreatorEmail;
    }

    public void setRequestCreatorEmail(String requestCreatorEmail) {
        this.requestCreatorEmail = requestCreatorEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}