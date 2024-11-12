package ca.mcgill.ecse321.gamestore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.model.Review;
import ca.mcgill.ecse321.gamestore.repository.ReviewRepository;
import ca.mcgill.ecse321.gamestore.repository.ProductRepository;
import ca.mcgill.ecse321.gamestore.repository.CustomerRepository;
import ca.mcgill.ecse321.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;

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
        String comment = "Great product!";
        
        // Create test customer and product
        Customer customer = new Customer("cust1", "Test Customer", email, "password");
        Product product = new Product();
        product.setId(productId);
        
        // Mock repository behaviors
        when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);
        when(productRepository.findProductById(productId)).thenReturn(product);
        when(reviewRepository.save(any(Review.class))).thenAnswer(i -> i.getArgument(0));

        // Create DTO with required fields
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setReviewWriterEmail(email);
        dto.setProductId(productId);
        dto.setRating(rating);
        dto.setComment(comment);

        // Act
        Review createdReview = reviewService.createReview(dto);

        // Assert
        assertNotNull(createdReview);
        assertEquals(rating, createdReview.getRating());
        assertEquals(comment, createdReview.getComments());
        assertEquals(email, createdReview.getReviewWriter().getEmail());
        assertEquals(productId, createdReview.getProduct().getId());
        verify(reviewRepository).save(any(Review.class));
        verify(customerRepository).findCustomerByEmail(email);
        verify(productRepository).findProductById(productId);
    }

    // should throw error when email is missing
    @Test
    public void testCreateReview_NullEmail() {
        // Arrange: Create request with missing email
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setProductId(1);
        dto.setRating(4);

        // Act & Assert
        GameStoreException e = assertThrows(GameStoreException.class, 
            () -> reviewService.createReview(dto));

        assertEquals("Review writer email cannot be empty.", e.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
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
        GameStoreException e = assertThrows(GameStoreException.class, 
            () -> reviewService.createReview(dto));

        assertEquals("Rating must be between 1 and 5.", e.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
    }

    // should throw error when there's no customer
    @Test
    public void testCreateReview_CustomerNotFound() {
        // Arrange: Set up request with non-existent customer
        ReviewRequestDto dto = new ReviewRequestDto();
        dto.setReviewWriterEmail("nonexistent@email.com");
        dto.setProductId(1);
        dto.setRating(4);
        
        when(customerRepository.findCustomerByEmail("nonexistent@email.com")).thenReturn(null);

        // Act & Assert: Verify customer not found exception
        GameStoreException e = assertThrows(GameStoreException.class, 
            () -> reviewService.createReview(dto));

        assertEquals("Customer not found with email: nonexistent@email.com", e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        verify(customerRepository, times(1)).findCustomerByEmail("nonexistent@email.com");
    }

    // tests getting all reviews for a specific product
    @Test
    public void testGetReviewsByProduct_Success() {
        // Arrange
        Integer productId = 1;
        Product product = new Product();
        product.setId(productId);
        
        Review review1 = new Review();
        review1.setRating(4);
        review1.setProduct(product);
        
        Review review2 = new Review();
        review2.setRating(5);
        review2.setProduct(product);
        
        when(productRepository.findProductById(productId)).thenReturn(product);
        when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

        // Act
        List<Review> reviews = reviewService.getReviewsByProduct(productId);

        // Assert
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        verify(productRepository).findProductById(productId);
        verify(reviewRepository).findAll();
    }

    // test getting reviews for product with no reviews
    @Test
    public void testGetReviewsByProduct_NoReviews() {
        // Arrange
        Integer productId = 1;
        Product product = new Product();
        product.setId(productId);
        
        when(productRepository.findProductById(productId)).thenReturn(product);
        when(reviewRepository.findAll()).thenReturn(List.of());

        // Act
        List<Review> reviews = reviewService.getReviewsByProduct(productId);

        // Assert
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
        verify(productRepository).findProductById(productId);
        verify(reviewRepository).findAll();
    }

    // get reviews by product w/ no product --> throws error
    @Test
    public void testGetReviewsByProduct_ProductNotFound() {
        // Arrange: Setup scenario with non-existent product
        Integer productId = 999;
        when(productRepository.findProductById(productId)).thenReturn(null);

        // Act & Assert: Verify product not found exception
        GameStoreException e = assertThrows(GameStoreException.class, 
            () -> reviewService.getReviewsByProduct(productId));

        assertEquals("Product not found with ID: " + productId, e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        verify(productRepository, times(1)).findProductById(productId);
    }

    // tests getting all reviews by a specific customer
    @Test
    public void testGetReviewsByCustomer_Success() {
        // Arrange
        String email = "customer@email.com";
        Customer customer = new Customer("cust1", "Test Customer", email, "password");
        
        Review review1 = new Review();
        review1.setRating(4);
        review1.setReviewWriter(customer);
        
        Review review2 = new Review();
        review2.setRating(5);
        review2.setReviewWriter(customer);
        
        when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);
        when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

        // Act
        List<Review> reviews = reviewService.getReviewsByCustomer(email);

        // Assert
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        verify(customerRepository).findCustomerByEmail(email);
        verify(reviewRepository).findAll();
    }

    // get reviews by customer, no customer --> throws error
    @Test
    public void testGetReviewsByCustomer_CustomerNotFound() {
        // Arrange: Setup scenario with non-existent customer
        String email = "nonexistent@email.com";
        when(customerRepository.findCustomerByEmail(email)).thenReturn(null);

        // Act & Assert: Verify customer not found exception
        GameStoreException e = assertThrows(GameStoreException.class, 
            () -> reviewService.getReviewsByCustomer(email));

        assertEquals("Customer not found with email: " + email, e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        verify(customerRepository, times(1)).findCustomerByEmail(email);
    }

    // test getting reviews for customer with no reviews
    @Test
    public void testGetReviewsByCustomer_NoReviews() {
        // Arrange
        String email = "customer@email.com";
        Customer customer = new Customer("cust1", "Test Customer", email, "password");
        
        when(customerRepository.findCustomerByEmail(email)).thenReturn(customer);
        when(reviewRepository.findAll()).thenReturn(List.of());

        // Act
        List<Review> reviews = reviewService.getReviewsByCustomer(email);

        // Assert
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
        verify(customerRepository).findCustomerByEmail(email);
        verify(reviewRepository).findAll();
    }

    // test getting a review by id with valid fields
    @Test
    public void testGetReviewById_Success() {
        // Arrange
        Integer reviewId = 1;
        Review review = new Review();
        review.setId(reviewId);
        review.setRating(4);
        review.setComments("Test review");
        
        when(reviewRepository.findReviewById(reviewId)).thenReturn(review);

        // Act
        Review result = reviewService.getReviewById(reviewId);

        // Assert
        assertNotNull(result);
        assertEquals(reviewId, result.getId());
        assertEquals(4, result.getRating());
        assertEquals("Test review", result.getComments());
        verify(reviewRepository).findReviewById(reviewId);
    }

    // test getting a review that doesn't exist
    @Test
    public void testGetReviewById_NotFound() {
        // Arrange
        Integer reviewId = 999;
        when(reviewRepository.findReviewById(reviewId)).thenReturn(null);

        // Act & Assert
        GameStoreException e = assertThrows(GameStoreException.class,
            () -> reviewService.getReviewById(reviewId));
        
        assertEquals("Review not found with ID: " + reviewId, e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        verify(reviewRepository).findReviewById(reviewId);
    }
    
    // test getting all reviews when they exist
    @Test
    public void testGetAllReviews_Success() {
        // Arrange
        Review review1 = new Review();
        review1.setRating(4);
        review1.setComments("First review");
        
        Review review2 = new Review();
        review2.setRating(5);
        review2.setComments("Second review");
        
        when(reviewRepository.findAll()).thenReturn(List.of(review1, review2));

        // Act
        List<Review> reviews = reviewService.getAllReviews();

        // Assert
        assertNotNull(reviews);
        assertEquals(2, reviews.size());
        verify(reviewRepository).findAll();
    }

    // test getting all reviews when none exist
    @Test
    public void testGetAllReviews_Empty() {
        // Arrange
        when(reviewRepository.findAll()).thenReturn(List.of());

        // Act
        List<Review> reviews = reviewService.getAllReviews();

        // Assert
        assertNotNull(reviews);
        assertTrue(reviews.isEmpty());
        verify(reviewRepository).findAll();
    }

    // tests successful deletion of review by manager
    @Test
    public void testDeleteReview_Success() {
        // Arrange
        Integer reviewId = 1;
        String managerEmail = "manager@email.com";
        
        Review review = new Review();
        review.setId(reviewId);
        
        Owner manager = new Owner("manager1", "Manager", managerEmail, "password");
        
        when(ownerRepository.findOwnerByEmail(managerEmail)).thenReturn(manager);
        when(reviewRepository.findReviewById(reviewId)).thenReturn(review);
        
        // Act & Assert
        assertDoesNotThrow(() -> reviewService.deleteReview(reviewId, managerEmail));
        verify(ownerRepository).findOwnerByEmail(managerEmail);  // Verifies manager lookup happened once
        verify(reviewRepository).findReviewById(reviewId);       // Verifies review lookup happened once
        verify(reviewRepository).deleteById(reviewId);           //  verifies deleteById was called 
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
        GameStoreException e = assertThrows(GameStoreException.class, 
            () -> reviewService.deleteReview(reviewId, managerEmail));

        assertEquals("Review not found with ID: " + reviewId, e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
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
        GameStoreException e = assertThrows(GameStoreException.class,
            () -> reviewService.deleteReview(reviewId, email));

        assertEquals("Only the manager can delete reviews.", e.getMessage());
        assertEquals(HttpStatus.BAD_REQUEST, e.getStatus());
        verify(ownerRepository, times(1)).findOwnerByEmail(email);
    }
}