package ca.mcgill.ecse321.gamestore.dto;

import java.sql.Date;
import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;
import ca.mcgill.ecse321.gamestore.model.Payment;

public class OrderResponseDto {

    private int orderId;
    private int number;
    private Date orderedDate;
    private Date shippedDate;
    private String shipTo;
    private double total;
    private OrderStatus status;
    private Integer paymentId; // Now Integer to handle possible null

    // Constructor to map from Order model to DTO
    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.number = order.getNumber();
        this.orderedDate = order.getOrderedDate();
        this.shippedDate = order.getShippedDate();
        this.shipTo = order.getShipTo();
        this.total = order.getTotal();
        this.status = order.getStatus();

        // Safely assign payment ID if it exists
        this.paymentId = (order.getPaymentOfOrder() != null) ?
                ((Payment) order.getPaymentOfOrder()).getId() : null;

    }

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

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public Date getShippedDate() {
        return shippedDate;
    }

    public void setShippedDate(Date shippedDate) {
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
}
