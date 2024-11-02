package ca.mcgill.ecse321.gamestore.dto;

/**
 * DTO for handling employee creation and update requests.
 */
public class EmployeeRequestDto {

    private String name;
    private String email;
    private String password;
    private String userID;

    // Default constructor required for serialization/deserialization
    public EmployeeRequestDto() {
    }

    // Constructor to initialize request data
    public EmployeeRequestDto(String name, String email, String password, String userID) {
        this.name = name;
        this.email = email;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
