package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.ShoppingCart;

import java.sql.Date;

public class ShoppingCartRequestDto {

    // Member Variables
    private Date creationDate;

    // Default Constructor


    // Constructor for creating a ShoppingCartDto from a ShoppingCart model
    public ShoppingCartRequestDto(Date creationDate) {
        this.creationDate = creationDate;
    }

    public ShoppingCartRequestDto(ShoppingCart shoppingCart) {
        this.creationDate = shoppingCart.getCreationDate();
    }

    public Date getCreationDate() {
        return creationDate;
    }
}

