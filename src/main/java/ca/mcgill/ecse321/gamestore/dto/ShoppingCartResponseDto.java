package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.ShoppingCart;

import java.sql.Date;

public class ShoppingCartResponseDto {
    private int cartId;

    // Member Variables
    private Date creationDate;

    // Default Constructor
    public ShoppingCartResponseDto() {}

    // Constructor for creating a ShoppingCartDto from a ShoppingCart model
    public ShoppingCartResponseDto(Date creationDate) {
        this.creationDate = creationDate;
    }
    public ShoppingCartResponseDto(ShoppingCart shoppingCart) {
        this.creationDate=shoppingCart.getCreationDate();
        this.cartId=shoppingCart.getId();
    }

    // Getters and Setters
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
    // Getters and Setters
    public int getId() {
        return cartId;
    }

    public void setId(int id) {
        this.cartId = id;
    }


    }

