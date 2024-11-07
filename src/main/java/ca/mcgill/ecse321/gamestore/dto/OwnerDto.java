package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Owner;

public class OwnerDto extends AccountDto {

    // CONSTRUCTORS
    public OwnerDto() {
        super();
    }


    public OwnerDto(String userID, String name, String email, String password) {
        super(name, userID, email, password);
    }

    public OwnerDto(Owner owner) {
        super(owner);
    }

}