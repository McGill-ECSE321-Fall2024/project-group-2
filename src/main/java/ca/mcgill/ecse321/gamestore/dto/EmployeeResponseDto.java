package ca.mcgill.ecse321.gamestore.dto;

/**
 * DTO for sending employee data in responses.
 */
public class EmployeeResponseDto {

    private String name;
    private String email;
    private String userID;

    // Default constructor for serialization/deserialization
    public EmployeeResponseDto() {
    }

    // Constructor to initialize response data
    public EmployeeResponseDto(String name, String email, String userID) {
        this.name = name;
        this.email = email;
        this.userID = userID;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
