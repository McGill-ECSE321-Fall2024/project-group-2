package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Person;

public class PersonDto {

    // VARIABLES
    private String email;
    private String name;
    private String userID;
    private String password;


    // CONSTRUCTORS
    public PersonDto() {
    }

    // Shift constructor requiring firstName, lastName, email, phoneNumber
    public PersonDto(String name, String userID, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.userID = userID;
    }

    // Shift constructor requiring General User
    public PersonDto(Person person) {
        this.name = person.getName();
        this.password = person.getPassword();
        this.email = person.getEmail();
        this.userID = person.getUserID();
    }


    // GETTERS
    public String getName() {
        return this.name;
    }

    public String getUserID() {
        return this.userID;
    }

    // Method to get email, returns email
    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    // Setters
    public boolean setEmail(String email) {
        this.email = email;
        return true;
    }

}