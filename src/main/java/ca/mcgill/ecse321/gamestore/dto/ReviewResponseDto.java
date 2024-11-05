
package ca.mcgill.ecse321.gamestore.dto;

import java.sql.Date;

import ca.mcgill.ecse321.gamestore.model.Review;

public class ReviewResponseDto {

    private Integer reviewId;
    private Integer rating;
    private String comments;
    private Date reviewDate;
    private String reviewWriterEmail; // Email of the Customer
    private Integer productId;        // ID of the Product

    // Default constructor
    public ReviewResponseDto() {
    }

    // Constructor to create DTO from model
    public ReviewResponseDto(Review review) {
        this.reviewId = review.getId();
        this.rating = review.getRating();
        this.comments = review.getComments();
        this.reviewDate = review.getReviewDate();
        this.reviewWriterEmail = review.getReviewWriter().getEmail();
        this.productId = review.getProduct().getId();
    }

    // Getters and Setters

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(Date reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getReviewWriterEmail() {
        return reviewWriterEmail;
    }

    public void setReviewWriterEmail(String reviewWriterEmail) {
        this.reviewWriterEmail = reviewWriterEmail;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
