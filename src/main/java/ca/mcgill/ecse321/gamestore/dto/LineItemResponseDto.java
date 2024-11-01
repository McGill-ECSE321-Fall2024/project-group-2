package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.LineItem;

public class LineItemResponseDto {

    private int lineItemId;
    private int quantity;
    private double price;
    private Integer cartId;
    private Integer wishlistId;

    // Constructor to map from LineItem model to DTO
    public LineItemResponseDto(LineItem lineItem) {
        this.lineItemId = lineItem.getId();
        this.quantity = lineItem.getQuantity();
        this.price = lineItem.getPrice();
        this.cartId = (lineItem.getCart() != null) ? lineItem.getCart().getId() : null;
        this.wishlistId = (lineItem.getWishlist() != null) ? lineItem.getWishlist().getId() : null;
    }

    // Getters
    public int getLineItemId() {
        return lineItemId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public Integer getCartId() {
        return cartId;
    }

    public Integer getWishlistId() {
        return wishlistId;
    }
}
