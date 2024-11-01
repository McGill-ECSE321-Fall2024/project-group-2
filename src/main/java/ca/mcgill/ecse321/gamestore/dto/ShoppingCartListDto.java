package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class ShoppingCartListDto {
    private List<ShoppingCartResponseDto> shoppingCart;

    public ShoppingCartListDto(List<ShoppingCartResponseDto> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }

    public List<ShoppingCartResponseDto> getShoppingCart() {
        return shoppingCart;
    }

    public void setShoppingCart(List <ShoppingCartResponseDto> shoppingCart) {
        this.shoppingCart = shoppingCart;
    }
}