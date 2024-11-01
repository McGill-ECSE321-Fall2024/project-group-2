package ca.mcgill.ecse321.gamestore.dto;

public class LineItemDto {
    private int id;
    private int quantity;
    private double price;
    private Integer orderId;
    private Integer cartId;
    private Integer wishlistId;

    public LineItemDto() {}

    public LineItemDto(int id, int quantity, double price, Integer orderId, Integer cartId, Integer wishlistId) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.orderId = orderId;
        this.cartId = cartId;
        this.wishlistId = wishlistId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(Integer wishlistId) {
        this.wishlistId = wishlistId;
    }
}
