package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.ReviewDto;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    /**
     * Creates a new Review.
     * @param dto Review data from the request body.
     * @return The created Review as a DTO.
     */
    @PostMapping
    public ReviewDto createReview(@RequestBody ReviewDto dto) {
        Review review = reviewService.createReview(dto);
        return new ReviewDto(review);
    }

    /**
     * Retrieves a Review by its ID.
     * @param id The ID of the review.
     * @return The Review as a DTO.
     */
    @GetMapping("/{id}")
    public ReviewDto getReviewById(@PathVariable Integer id) {
        Review review = reviewService.getReviewById(id);
        return new ReviewDto(review);
    }

    /**
     * Retrieves all Reviews.
     * @return A list of Review DTOs.
     */
    @GetMapping
    public List<ReviewDto> getAllReviews() {
        return reviewService.getAllReviews().stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all Reviews for a specific Product.
     * @param productId The ID of the product.
     * @return A list of Review DTOs.
     */
    @GetMapping("/product/{productId}")
    public List<ReviewDto> getReviewsByProduct(@PathVariable Integer productId) {
        return reviewService.getReviewsByProduct(productId).stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves all Reviews by a specific Customer.
     * @param customerEmail The email of the customer.
     * @return A list of Review DTOs.
     */
    @GetMapping("/customer/{customerEmail}")
    public List<ReviewDto> getReviewsByCustomer(@PathVariable String customerEmail) {
        return reviewService.getReviewsByCustomer(customerEmail).stream()
                .map(ReviewDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a Review by its ID. Only the manager can perform this action.
     * @param id The ID of the review to delete.
     * @param managerEmail The email of the manager requesting the deletion.
     */
    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Integer id, @RequestParam String managerEmail) {
        reviewService.deleteReview(id, managerEmail);
    }
}
