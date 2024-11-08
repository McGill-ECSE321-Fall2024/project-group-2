package ca.mcgill.ecse321.gamestore.integration;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dto.ReviewRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ReviewListDto;
import ca.mcgill.ecse321.gamestore.model.*;
import ca.mcgill.ecse321.gamestore.service.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReviewIntegrationTests {

    // Get test rest template to make requests
    @Autowired
    private TestRestTemplate client;

    // Get all services needed to setup test database
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OwnerService ownerService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private LineItemService lineItemService;

    // Define test values as constants
    private final String TEST_CUSTOMER_EMAIL = "test.customer@email.com";
    private final String TEST_MANAGER_EMAIL = "test.manager@email.com";
    private Integer testProductId;
    private Integer testLineItemId;
    private Integer testCategoryId;

    // Set up test data before each test
    @BeforeEach
    public void setupTestData() {
        // Create a test customer
        customerService.createCustomer(
            "testuser", 
            "Test Customer",
            TEST_CUSTOMER_EMAIL,
            "password123"
        );

        // Create a test manager
        ownerService.createOwner(
            "manager1",
            "Test Manager",
            TEST_MANAGER_EMAIL,
            "password123"
        );

        // Create a test category
        Category category = categoryService.createCategory("Test Category");
        testCategoryId = category.getId();

        // Create a test line item
        LineItem lineItem = lineItemService.createLineItem(1, 10.0);
        testLineItemId = lineItem.getId();

        // Create a test product with all required dependencies
        Product product = productService.createProduct(
            "Test Product",
            "Test Description",
            lineItem,
            category
        );
        testProductId = product.getId();
    }

    // Clean up database after each test
    @AfterEach
    public void clearDatabase() {
        // Delete reviews first (foreign key constraint)
        reviewService.getAllReviews().forEach(review -> 
            reviewService.deleteReview(review.getId(), TEST_MANAGER_EMAIL)
        );

        // Delete other test data in order
        productService.deleteProduct(testProductId);
        lineItemService.deleteLineItem(testLineItemId);
        categoryService.deleteCategory(testCategoryId);
        customerService.deleteCustomer(TEST_CUSTOMER_EMAIL);
        ownerService.deleteOwner(TEST_MANAGER_EMAIL);
    }

    // Test creating a review with valid input
    @Test
    public void testCreateReview_Success() {
        // Setup the request with valid data
        ReviewRequestDto request = new ReviewRequestDto();
        request.setReviewWriterEmail(TEST_CUSTOMER_EMAIL);
        request.setProductId(testProductId);
        request.setRating(4);
        request.setComments("Great product!");

        // Send POST request to create review
        ResponseEntity<ReviewResponseDto> response = client.postForEntity(
            "/reviews",
            request,
            ReviewResponseDto.class
        );

        // Check that the review was created properly
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        ReviewResponseDto created = response.getBody();
        assertNotNull(created);
        assertNotNull(created.getReviewId());
        assertEquals(request.getComments(), created.getComments());
        assertEquals(request.getRating(), created.getRating());
        assertEquals(request.getReviewWriterEmail(), created.getReviewWriterEmail());
        assertEquals(request.getProductId(), created.getProductId());
    }

    // test creating review with invalid rating
    @Test
    public void testCreateReview_InvalidRating() {
        // Setup request with invalid rating
        ReviewRequestDto request = createReviewRequest("Test review", 6);

        // Send POST request
        ResponseEntity<String> response = client.postForEntity(
            "/reviews",
            request,
            String.class
        );

        // Check error response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Rating must be between 1 and 5.", response.getBody());
    }

    // test creating review with non-existent product
    @Test
    public void testCreateReview_NonExistentProduct() {
        // Setup request with non-existent product
        ReviewRequestDto request = createReviewRequest("Test review", 4);
        request.setProductId(999);

        // Send POST request
        ResponseEntity<String> response = client.postForEntity(
            "/reviews",
            request,
            String.class
        );

        // Check error response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product not found with ID: 999", response.getBody());
    }

    // test creating review with non-existent customer
    @Test
    public void testCreateReview_NonExistentCustomer() {
        // Setup request with non-existent customer
        ReviewRequestDto request = createReviewRequest("Test review", 4);
        request.setReviewWriterEmail("nonexistent@email.com");

        // Send POST request
        ResponseEntity<String> response = client.postForEntity(
            "/reviews",
            request,
            String.class
        );

        // Check error response
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Customer not found with email: nonexistent@email.com", response.getBody());
    }

    // Test getting a review by ID
    @Test
    public void testGetReview_Success() {
        // Create a review first
        ReviewRequestDto request = new ReviewRequestDto();
        request.setReviewWriterEmail(TEST_CUSTOMER_EMAIL);
        request.setProductId(testProductId);
        request.setRating(4);
        request.setComments("Test review");

        ResponseEntity<ReviewResponseDto> createResponse = client.postForEntity(
            "/reviews",
            request,
            ReviewResponseDto.class
        );
        Integer reviewId = createResponse.getBody().getReviewId();

        // Get the created review
        ResponseEntity<ReviewResponseDto> response = client.getForEntity(
            "/reviews/" + reviewId,
            ReviewResponseDto.class
        );

        // Check that we got the right review back
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto retrieved = response.getBody();
        assertNotNull(retrieved);
        assertEquals(reviewId, retrieved.getReviewId());
        assertEquals(request.getComments(), retrieved.getComments());
        assertEquals(request.getRating(), retrieved.getRating());
    }

    // Test getting all reviews
    @Test
    public void testGetAllReviews() {
        // Create two test reviews
        ReviewRequestDto request1 = createReviewRequest("First review", 4);
        ReviewRequestDto request2 = createReviewRequest("Second review", 5);

        client.postForEntity("/reviews", request1, ReviewResponseDto.class);
        client.postForEntity("/reviews", request2, ReviewResponseDto.class);

        // Get all reviews
        ResponseEntity<ReviewListDto> response = client.getForEntity(
            "/reviews",
            ReviewListDto.class
        );

        // Check that we got both reviews
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewListDto reviews = response.getBody();
        assertNotNull(reviews);
        assertEquals(2, reviews.getReviews().size());
    }

    // Test getting reviews by product
    @Test
    public void testGetReviewsByProduct() {
        // Create a review for our test product
        ReviewRequestDto request = createReviewRequest("Product review", 4);
        client.postForEntity("/reviews", request, ReviewResponseDto.class);

        // Get reviews for the product
        ResponseEntity<ReviewListDto> response = client.getForEntity(
            "/reviews/product/" + testProductId,
            ReviewListDto.class
        );

        // Check that we got the right review
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewListDto reviews = response.getBody();
        assertNotNull(reviews);
        assertFalse(reviews.getReviews().isEmpty());
        assertEquals(testProductId, reviews.getReviews().get(0).getProductId());
    }

    // test getting reviews by customer
    @Test
    public void testGetReviewsByCustomer() {
        // Create a review from our test customer
        ReviewRequestDto request = createReviewRequest("Customer review", 4);
        client.postForEntity("/reviews", request, ReviewResponseDto.class);

        // Get reviews for the customer
        ResponseEntity<ReviewListDto> response = client.getForEntity(
            "/reviews/customer/" + TEST_CUSTOMER_EMAIL,
            ReviewListDto.class
        );

        // Check response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewListDto reviews = response.getBody();
        assertNotNull(reviews);
        assertFalse(reviews.getReviews().isEmpty());
        assertEquals(TEST_CUSTOMER_EMAIL, reviews.getReviews().get(0).getReviewWriterEmail());
    }
    

    // Test deleting a review as manager
    @Test
    public void testDeleteReview_AsManager() {
        // Create a review to delete
        ReviewRequestDto request = createReviewRequest("To be deleted", 4);
        ResponseEntity<ReviewResponseDto> createResponse = client.postForEntity(
            "/reviews",
            request,
            ReviewResponseDto.class
        );
        Integer reviewId = createResponse.getBody().getReviewId();

        // Delete the review
        ResponseEntity<String> response = client.exchange(
            "/reviews/" + reviewId + "?managerEmail=" + TEST_MANAGER_EMAIL,
            org.springframework.http.HttpMethod.DELETE,
            null,
            String.class
        );

        // Check deletion was successful
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Review with ID " + reviewId + " deleted successfully.", response.getBody());

        // Try to get the deleted review - should fail
        ResponseEntity<String> getResponse = client.getForEntity(
            "/reviews/" + reviewId,
            String.class
        );
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }

    // Helper method to create a review request
    private ReviewRequestDto createReviewRequest(String comments, int rating) {
        ReviewRequestDto request = new ReviewRequestDto();
        request.setReviewWriterEmail(TEST_CUSTOMER_EMAIL);
        request.setProductId(testProductId);
        request.setRating(rating);
        request.setComments(comments);
        return request;
    }

    // test deleting review as non-manager
    @Test
    public void testDeleteReview_NonManager() {
        // Create a review first
        ReviewRequestDto request = createReviewRequest("To be deleted", 4);
        ResponseEntity<ReviewResponseDto> createResponse = client.postForEntity(
            "/reviews",
            request,
            ReviewResponseDto.class
        );
        Integer reviewId = createResponse.getBody().getReviewId();

        // Try to delete with non-manager email
        ResponseEntity<String> response = client.exchange(
            "/reviews/" + reviewId + "?managerEmail=notmanager@email.com",
            org.springframework.http.HttpMethod.DELETE,
            null,
            String.class
        );

        // Check error response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Only the manager can delete reviews.", response.getBody());
    }
}