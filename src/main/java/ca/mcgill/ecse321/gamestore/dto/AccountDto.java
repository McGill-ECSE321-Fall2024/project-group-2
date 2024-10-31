package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Account;
import ca.mcgill.ecse321.gamestore.model.Person;

public class AccountDto extends PersonDto {

    // VARIABLES
    private String password;

    // CONSTRUCTORS
    public AccountDto() {
        super();
    }

    public AccountDto(String Name, String UserID, String email,  String password) {
        super(Name,UserID, email, password);
        this.password = password;
    }

    public AccountDto(Person person) {
        super(person);
    }

    // Setters
    public boolean setPassword(String password) {
        this.password = password;
        return true;
    }

    // Getters
    public String getPassword() {
        return this.password;
    }

    public String getEmail() {
        return super.getEmail();
    }
}