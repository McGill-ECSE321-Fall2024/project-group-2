package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewListDto;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.service.ReviewService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto dto) {
        Review review = reviewService.createReview(dto);
        return new ReviewResponseDto(review);
    }

    @GetMapping("/{id}")
    public ReviewResponseDto getReviewById(@PathVariable Integer id) {
        Review review = reviewService.getReviewById(id);
        return new ReviewResponseDto(review);
    }

    @GetMapping
    public ReviewListDto getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        List<ReviewResponseDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewResponseDto(review));
        }
        return new ReviewListDto(reviewDtos);
    }

    @GetMapping("/product/{productId}")
    public ReviewListDto getReviewsByProduct(@PathVariable Integer productId) {
        List<Review> reviews = reviewService.getReviewsByProduct(productId);
        List<ReviewResponseDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewResponseDto(review));
        }
        return new ReviewListDto(reviewDtos);
    }

    @GetMapping("/customer/{customerEmail}")
    public ReviewListDto getReviewsByCustomer(@PathVariable String customerEmail) {
        List<Review> reviews = reviewService.getReviewsByCustomer(customerEmail);
        List<ReviewResponseDto> reviewDtos = new ArrayList<>();
        for (Review review : reviews) {
            reviewDtos.add(new ReviewResponseDto(review));
        }
        return new ReviewListDto(reviewDtos);
    }

    @PutMapping("/{id}")
    public ReviewResponseDto updateReview(@PathVariable Integer id, @RequestBody ReviewRequestDto dto) {
        Review review = reviewService.updateReview(id, dto);
        return new ReviewResponseDto(review);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Integer id, @RequestParam String managerEmail) {
        reviewService.deleteReview(id, managerEmail);
    }
}