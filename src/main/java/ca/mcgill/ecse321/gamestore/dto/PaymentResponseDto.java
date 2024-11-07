package ca.mcgill.ecse321.gamestore.dto;

import java.sql.Date;

public class PaymentResponseDto {

    private int paymentId;
    private Date paidDate;
    private double total;
    private String details;

    public PaymentResponseDto() {}

    // Constructor to initialize a response DTO from a Payment entity
    public PaymentResponseDto(int paymentId, Date paidDate, double total, String details) {
        this.paymentId = paymentId;
        this.paidDate = paidDate;
        this.total = total;
        this.details = details;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public Date getPaidDate() {
        return paidDate;
    }

    public void setPaidDate(Date paidDate) {
        this.paidDate = paidDate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
