package ca.mcgill.ecse321.gamestore.dto;

public class ChangeRequestRequestDto {
    private String requestCreatorEmail; // Email of the Employee (requestCreator)
    
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

    
}