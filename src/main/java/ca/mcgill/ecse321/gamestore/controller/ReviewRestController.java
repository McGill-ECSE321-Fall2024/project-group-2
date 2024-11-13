package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewListDto;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReviewRestController {

    @Autowired
    private ReviewService reviewService;

    // create review, return response dto
    @PostMapping("/reviews")
    @ResponseStatus(HttpStatus.CREATED)
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto dto) {
        Review review = reviewService.createReview(dto);
        return new ReviewResponseDto(review);
    }

    // get review by ID, return response dto
    @GetMapping("/reviews/{id}")
    public ReviewResponseDto getReviewById(@PathVariable Integer id) {
        Review review = reviewService.getReviewById(id);
        return new ReviewResponseDto(review);
    }

    // get reviews, return as list dto
    @GetMapping("/reviews")
    public ReviewListDto getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        List<ReviewResponseDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewResponseDto(review));
        }
        return new ReviewListDto(reviewDtos);
    }

    // get reviews by product, return list dto
    @GetMapping("/products/{productId}/reviews")
    public ReviewListDto getReviewsByProduct(@PathVariable Integer productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        List<ReviewResponseDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewResponseDto(review));
        }
        return new ReviewListDto(reviewDtos);
    }

    // get reviews by customer, return list dto
    @GetMapping("/customers/{customerEmail}/reviews")
    public ReviewListDto getReviewsByCustomer(@PathVariable String customerEmail) {
        List<Review> reviews = reviewService.getReviewsByCustomer(customerEmail);
        List<ReviewResponseDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewResponseDto(review));
        }
        return new ReviewListDto(reviewDtos);
    }

    // delete review, returns confirmation message
    @DeleteMapping("/reviews/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteReview(@PathVariable Integer id, @RequestParam String managerEmail) {
        reviewService.deleteReview(id, managerEmail);
        return "Review with ID " + id + " deleted successfully.";
    }
}