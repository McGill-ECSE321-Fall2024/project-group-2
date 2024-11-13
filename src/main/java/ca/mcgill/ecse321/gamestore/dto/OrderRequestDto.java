package ca.mcgill.ecse321.gamestore.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

public class OrderRequestDto {

    private int number;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderedDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate shippedDate;
    private String shipTo;
    private double total;
    private OrderStatus status;
    private int paymentId;

    public OrderRequestDto() {}

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

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

}
