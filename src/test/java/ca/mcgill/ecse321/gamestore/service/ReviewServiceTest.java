package ca.mcgill.ecse321.gamestore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.model.Owner;
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
        // [Rest of this test stays exactly the same as it tests the success case]
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

    // [Success test methods stay exactly the same as they don't test exceptions]

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

    // get reviews by customer, no customer --> throws error
    @Test
    public void testGetReviewsByCustomer_CustomerNotFound() {
        // Arrange: Setup scenario with non-existent customer
        String email = "nonexistent@email.com";
        when(customerRepository.findAll()).thenReturn(List.of());

        // Act & Assert: Verify customer not found exception
        GameStoreException e = assertThrows(GameStoreException.class, 
            () -> reviewService.getReviewsByCustomer(email));

        assertEquals("Customer not found with email: " + email, e.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, e.getStatus());
        verify(customerRepository, times(1)).findCustomerByEmail(email);
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