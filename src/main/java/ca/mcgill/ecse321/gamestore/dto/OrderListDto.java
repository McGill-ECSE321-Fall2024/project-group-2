package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class OrderListDto {
    private List<OrderResponseDto> orders;

    public OrderListDto(List<OrderResponseDto> orders) {
        this.orders = orders;
    }

    public List<OrderResponseDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderResponseDto> orders) {
        this.orders = orders;
    }
}
