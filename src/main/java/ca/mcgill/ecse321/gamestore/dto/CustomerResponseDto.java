package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Customer;

/**
 * DTO for customer responses.
 * Used to send customer information while hiding sensitive details (e.g., password).
 */
public class CustomerResponseDto {

    private String userID;
    private String name;
    private String email;


    public CustomerResponseDto() {
    }

    // Constructor to convert Customer entity to DTO
    public CustomerResponseDto(Customer customer) {
        this.userID = customer.getUserID();
        this.name = customer.getName();
        this.email = customer.getEmail();
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
}

