package ca.mcgill.ecse321.gamestore.dto;

import java.sql.Date;

public class PaymentRequestDto {

    private Date paidDate;
    private double total;
    private String details;

    // Default constructor for serialization/deserialization
    public PaymentRequestDto() {}

    // Parameterized constructor for creating a request
    public PaymentRequestDto(Date paidDate, double total, String details) {
        this.paidDate = paidDate;
        this.total = total;
        this.details = details;
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
