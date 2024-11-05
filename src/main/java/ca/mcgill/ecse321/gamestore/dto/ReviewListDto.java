package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class ReviewListDto {
    private List<ReviewResponseDto> reviews;

    // Default constructor
    public ReviewListDto() {
    }

    // Constructor with reviews
    public ReviewListDto(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }

    // Getter and setter
    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }
}