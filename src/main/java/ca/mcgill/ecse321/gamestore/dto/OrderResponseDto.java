package ca.mcgill.ecse321.gamestore.dto;


import java.time.LocalDate;

import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

public class OrderResponseDto {

    private int orderId;
    private int number;
    
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderedDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate shippedDate;
    private String shipTo;
    private double total;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OrderStatus status;
    private Integer paymentId; // Now Integer to handle possible null

    // Constructor to map from Order model to DTO
    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.number = order.getNumber();
        this.orderedDate = convertToLocalDate(order.getOrderedDate());
        this.shippedDate = convertToLocalDate(order.getShippedDate());
        this.shipTo = order.getShipTo();
        this.total = order.getTotal();
        this.status = order.getStatus();

        // Safely assign payment ID if it exists
        if (order.getPaymentOfOrder() != null) {
            this.paymentId = order.getPaymentOfOrder().getId();
        }

    }
    public OrderResponseDto() {}
    // Getters and Setters for DTO fields
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public LocalDate getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(LocalDate orderedDate) {
        this.orderedDate = orderedDate;
    }

    public LocalDate getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(LocalDate shippedDate) {
        this.shippedDate = shippedDate;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    private LocalDate convertToLocalDate(java.sql.Date date) {
        if (date != null) {
            return date.toLocalDate();
        } else {
            return null;
        }
    }

}
