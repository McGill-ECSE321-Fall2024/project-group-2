package ca.mcgill.ecse321.gamestore.dto;

public class ReviewRequestDto {

    private Integer rating;
    private String comments;
    private String reviewWriterEmail; // Email of the Customer
    private Integer productId;        // ID of the Product

    // Default constructor
    public ReviewRequestDto() {
    }

    // Getters and Setters

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

