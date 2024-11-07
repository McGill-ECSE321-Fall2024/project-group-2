package ca.mcgill.ecse321.gamestore.dto;

/**
 * DTO for incoming customer requests.
 * Used for creating or updating customer information.
 */
public class CustomerRequestDto {

    private String userID;
    private String name;
    private String email;
    private String password;

    public CustomerRequestDto() {
    }

    // Parameterized constructor
    public CustomerRequestDto(String userID, String name, String email, String password) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

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
}

