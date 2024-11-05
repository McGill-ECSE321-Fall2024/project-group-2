package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321.gamestore.repository.ProductRepository;
import ca.mcgill.ecse321.gamestore.repository.ReviewRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Transactional
    public Review createReview(ReviewRequestDto dto) {
        // Validate inputs
        if (dto.getReviewWriterEmail() == null || dto.getReviewWriterEmail().isEmpty()) {
            throw new IllegalArgumentException("Review writer email cannot be empty.");
        }
        if (dto.getProductId() == null) {
            throw new IllegalArgumentException("Product ID cannot be null.");
        }
        if (dto.getRating() == null) {
            throw new IllegalArgumentException("Rating cannot be null.");
        }
        if (dto.getRating() < 1 || dto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        // Fetch associated Customer (reviewWriter)
        Customer reviewWriter = findCustomerByEmail(dto.getReviewWriterEmail());
        if (reviewWriter == null) {
            throw new IllegalArgumentException("Customer not found with email: " + dto.getReviewWriterEmail());
        }

        // Fetch associated Product
        Product product = productRepository.findProductById(dto.getProductId());
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + dto.getProductId());
        }

        // Create new Review
        Review review = new Review();
        review.setRating(dto.getRating());
        review.setComments(dto.getComments());

        // Set current date as java.sql.Date
        Date sqlDate = new Date(System.currentTimeMillis());
        review.setReviewDate(sqlDate);

        review.setReviewWriter(reviewWriter);
        review.setProduct(product);

        // Save to repository
        return reviewRepository.save(review);
    }

    @Transactional
    public Review updateReview(Integer reviewId, ReviewRequestDto dto) {
        // Validate review exists
        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new IllegalArgumentException("Review not found with ID: " + reviewId);
        }
        
        // Validate inputs
        if (dto.getRating() == null) {
            throw new IllegalArgumentException("Rating cannot be null.");
        }
        if (dto.getRating() < 1 || dto.getRating() > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        // Update review fields
        review.setRating(dto.getRating());
        review.setComments(dto.getComments());

        // Save and return updated review
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public Review getReviewById(Integer reviewId) {
        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new IllegalArgumentException("Review not found with ID: " + reviewId);
        }
        return review;
    }

    @Transactional(readOnly = true)
    public List<Review> getAllReviews() {
        Iterable<Review> iterable = reviewRepository.findAll();
        List<Review> reviews = new ArrayList<>();
        for (Review review : iterable) {
            reviews.add(review);
        }
        return reviews;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByProduct(Integer productId) {
        // Fetch associated Product
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new IllegalArgumentException("Product not found with ID: " + productId);
        }

        // Fetch all reviews and filter by product
        Iterable<Review> allReviews = reviewRepository.findAll();
        List<Review> productReviews = new ArrayList<>();
        for (Review review : allReviews) {
            if (review.getProduct() != null && review.getProduct().getId().equals(product.getId())) {
                productReviews.add(review);
            }
        }
        return productReviews;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByCustomer(String customerEmail) {
        // Fetch associated Customer
        Customer customer = findCustomerByEmail(customerEmail);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with email: " + customerEmail);
        }

        // Fetch all reviews and filter by customer
        Iterable<Review> allReviews = reviewRepository.findAll();
        List<Review> customerReviews = new ArrayList<>();
        for (Review review : allReviews) {
            if (review.getReviewWriter().getEmail().equals(customer.getEmail())) {
                customerReviews.add(review);
            }
        }
        return customerReviews;
    }

    @Transactional
    public void deleteReview(Integer reviewId, String managerEmail) {
        // Verify that the manager exists
        Owner manager = findOwnerByEmail(managerEmail);
        if (manager == null) {
            throw new IllegalArgumentException("Only the manager can delete reviews.");
        }

        // Verify review exists before deletion
        Review review = reviewRepository.findReviewById(reviewId);
        if (review == null) {
            throw new IllegalArgumentException("Review not found with ID: " + reviewId);
        }
        
        reviewRepository.deleteById(reviewId);
    }

    // Helper method to find Customer by email
    private Customer findCustomerByEmail(String email) {
        Iterable<Customer> customers = customerRepository.findAll();
        for (Customer customer : customers) {
            if (customer.getEmail().equals(email)) {
                return customer;
            }
        }
        return null;
    }

    // Helper method to find Owner by email
    private Owner findOwnerByEmail(String email) {
        Iterable<Owner> owners = ownerRepository.findAll();
        for (Owner owner : owners) {
            if (owner.getEmail().equals(email)) {
                return owner;
            }
        }
        return null;
    }
}