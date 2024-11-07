package ca.mcgill.ecse321.gamestore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.repository.ReviewRepository;
import ca.mcgill.ecse321.gamestore.repository.ProductRepository;
import ca.mcgill.ecse321.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321.gamestore.repository.OwnerRepository;


@SpringBootTest
public class ReviewServiceTest {

    // Mock repositories to simulate database interactions
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private OwnerRepository ownerRepository;

    // service being tested
    @InjectMocks
    private ReviewService reviewService;

    // tests creation of review with all valid fields
    @Test
    public void testCreateValidReview() {
        // Arrange
        String email = "customer@email.com";
        Integer productId = 1;
        Integer rating = 4;
        String comments = "Great product!";
        
        // Create test customer and product
        Customer customer = new Customer("user1", "Test Customer", email, "password");
        Product product = new Product();
        product.setId(productId);
        
        // Mock repository behaviors for successful path
        when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);
        when(productRepository.findProductById(productId)).thenReturn(product);
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));

        // Create DTO with all required fields
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setReviewWriterEmail(email);
        dto.setProductId(productId);
        dto.setRating(rating);
        dto.setComments(comments);

        // Act
        Review createdReview = reviewService.createReview(dto);

        // Assert
        assertNotNull(createdReview);
        assertEquals(rating, createdReview.getRating());
        assertEquals(comments, createdReview.getComments());
        assertEquals(email, createdReview.getReviewWriter().getEmail());
        assertEquals(productId, createdReview.getProduct().getId());
        verify(reviewRepository, times(1)).save(any(Review.class));
        verify(customerRepository, times(1)).findCustomerByEmail(email);
        verify(productRepository, times(1)).findProductById(productId);

    }

    // should throw error when email is missing
    @Test
    public void testCreateReview_NullEmail() {
        // Arrange: Create request with missing email
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setProductId(1);
        dto.setRating(4);

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> reviewService.createReview(dto));
        assertEquals("Review writer email cannot be empty.", e.getMessage());
    }

    // review rating must be between 1 and 5, throw error when not
    @Test
    public void testCreateReview_InvalidRating() {
        // Arrange
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setReviewWriterEmail("test@email.com");
        dto.setProductId(1);
        dto.setRating(6);  // Invalid rating > 5

        // Act & Assert
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> reviewService.createReview(dto));
        assertEquals("Rating must be between 1 and 5.", e.getMessage());
    }

    // should throw error when ther's no customer
    @Test
    public void testCreateReview_CustomerNotFound() {
        // Arrange: Set up request with non-existent customer
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setReviewWriterEmail("nonexistent@email.com");
        dto.setProductId(1);
        dto.setRating(4);
        
        when(customerRepository.findCustomerByEmail("nonexistent@email.com")).thenReturn(null);

        // Act & Assert: Verify customer not found exception
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> reviewService.createReview(dto));
        assertEquals("Customer not found with email: nonexistent@email.com", e.getMessage());
        verify(customerRepository, times(1)).findCustomerByEmail("nonexistent@email.com");
    }


    // get review by id with all valid fields
    @Test
    public void testGetReviewById_Success() {
        // Arrange
        Integer reviewId = 1;
        Review review = new Review();
        review.setId(reviewId);
        review.setRating(4);
        review.setComments("Test comment");
        
        when(reviewRepository.findReviewById(reviewId)).thenReturn(review);

        // Act
        Review result = reviewService.getReviewById(reviewId);

        // Assert
        assertNotNull(result);
        assertEquals(reviewId, result.getId());
        assertEquals(4, result.getRating());
        assertEquals("Test comment", result.getComments());
        verify(reviewRepository, times(1)).findReviewById(reviewId);
    }

    // get all reviews with valid fileds
    @Test
    public void testGetAllReviews_Success() {
        // Arrange
        Review review1 = new Review();
        review1.setRating(4);
        Review review2 = new Review();
        review2.setRating(5);
        when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

        // Act
        List<Review> reviews = reviewService.getAllReviews();

        // Assert: Verify correct list returned
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        verify(reviewRepository, times(1)).findAll();
    }

    // get reviews by product with valid fields
    @Test
    public void testGetReviewsByProduct_Success() {
        // Arrange: Set up product and associated review
        Integer productId = 1;
        String reviewComment = "Great product!";
        
        Product product = new Product();
        product.setId(productId);
        product.setName("Test Product");
        
        Review review = new Review();
        review.setProduct(product);
        review.setComments(reviewComment);
        review.setRating(4);
        
        when(productRepository.findProductById(productId)).thenReturn(product);
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        // Act: Retrieve product reviews
        List<Review> reviews = reviewService.getReviewsByProduct(productId);

        // Assert
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(productId, reviews.get(0).getProduct().getId());
        assertEquals(reviewComment, reviews.get(0).getComments());
        verify(productRepository, times(1)).findProductById(productId);
        verify(reviewRepository, times(1)).findAll();
    }

    // get reviews by product w/ no product --> throws error
    @Test
    public void testGetReviewsByProduct_ProductNotFound() {
        // Arrange: Setup scenario with non-existent product
        Integer productId = 999;
        when(productRepository.findProductById(productId)).thenReturn(null);

        // Act & Assert: Verify product not found exception
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> reviewService.getReviewsByProduct(productId));
        assertEquals("Product not found with ID: " + productId, e.getMessage());
        verify(productRepository, times(1)).findProductById(productId);
    }

    // get reviews by customer, all valid fields
    @Test
    public void testGetReviewsByCustomer_Success() {
        // Arrange: Set up customer and associated review
        String email = "customer@email.com";
        String reviewComment = "Great product!";
        
        Customer customer = new Customer();
        customer.setEmail(email);
        customer.setName("Test Customer");
        
        Review review = new Review();
        review.setReviewWriter(customer);
        review.setComments(reviewComment);
        review.setRating(4);
        
        when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        // Act: Retrieve customer reviews
        List<Review> reviews = reviewService.getReviewsByCustomer(email);

        // Assert: Verify correct reviews returned
        assertNotNull(reviews);
        assertEquals(1, reviews.size());
        assertEquals(email, reviews.get(0).getReviewWriter().getEmail());
        assertEquals(reviewComment, reviews.get(0).getComments());
        verify(customerRepository, times(1)).findCustomerByEmail(email);
        verify(reviewRepository, times(1)).findAll();
    }

    // get reviews by customer, no customer --> throws error
    @Test
    public void testGetReviewsByCustomer_CustomerNotFound() {
        // Arrange: Setup scenario with non-existent customer
        String email = "nonexistent@email.com";
        when(customerRepository.findAll()).thenReturn(List.of());

        // Act & Assert: Verify customer not found exception
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> reviewService.getReviewsByCustomer(email));
        assertEquals("Customer not found with email: " + email, e.getMessage());
        verify(customerRepository, times(1)).findCustomerByEmail(email);;
    }

    // succesful deletion by manager
    @Test
    public void testDeleteReview_Success() {
        // Arrange: Set up review and manager for deletion
        Integer reviewId = 1;
        String managerEmail = "manager@email.com";
        
        Review review = new Review();
        review.setId(reviewId);
        review.setComments("Test review");
        
        Owner manager = new Owner("manager1", "Manager", managerEmail, "password");
        
        when(ownerRepository.findOwnerByEmail(managerEmail)).thenReturn(manager);
        when(reviewRepository.findReviewById(reviewId)).thenReturn(review);

        // Act: Attempt deletion
        reviewService.deleteReview(reviewId, managerEmail);

        // Assert
        verify(ownerRepository, times(1)).findOwnerByEmail(managerEmail);
        verify(reviewRepository, times(1)).findReviewById(reviewId);
        verify(reviewRepository, times(1)).deleteById(reviewId);
    }

    // try and delete non-existent review --> throws error
    @Test
    public void testDeleteReview_ReviewNotFound() {
        // Arrange: Setup deletion of non-existent review
        Integer reviewId = 999;
        String managerEmail = "manager@email.com";
        Owner manager = new Owner("manager1", "Manager", managerEmail, "password");
        
        when(ownerRepository.findOwnerByEmail(managerEmail)).thenReturn(manager);
        when(reviewRepository.findReviewById(reviewId)).thenReturn(null);

        // Act & Assert: Verify review not found exception
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, 
            () -> reviewService.deleteReview(reviewId, managerEmail));
        assertEquals("Review not found with ID: " + reviewId, e.getMessage());
        verify(ownerRepository, times(1)).findOwnerByEmail(managerEmail);
        verify(reviewRepository, times(1)).findReviewById(reviewId);
    }

    // try and delete by non-manager --> throws error
    @Test
    public void testDeleteReview_NotManager() {
        // Arrange: Setup deletion attempt by non-manager
        Integer reviewId = 1;
        String email = "notmanager@email.com";
        when(ownerRepository.findOwnerByEmail(email)).thenReturn(null);

        // Act & Assert: Verify unauthorized exception
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class,
            () -> reviewService.deleteReview(reviewId, email));
        assertEquals("Only the manager can delete reviews.", e.getMessage());
        verify(ownerRepository, times(1)).findOwnerByEmail(email);
    }
}