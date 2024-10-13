package ca.mcgill.ecse321.gamestore.repository;

// Import necessary assertions for test validation
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//Import gamestore model
import ca.mcgill.ecse321.gamestore.model.*;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Import Date used for date data type
import java.sql.Date;

/**
 * This class contains unit tests for the ReviewRepository to ensure that
 * Review entities are correctly persisted and retrieved from the database.
 */
@SpringBootTest
class ReviewRepositoryApplicationTests {

    // Autowired dependencies for interacting with the Review and Customer repositories
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CustomerRepository customerRepository;

    /**
     * Clears the database before and after each test to ensure a clean environment.
     * This method deletes all relevant entities from the repositories.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        reviewRepository.deleteAll();
        customerRepository.deleteAll();
    }


    /**
     * Tests that a Review entity can be saved and retrieved from the database.
     * It validates the fields of the retrieved Review against the saved Review.
     */
    @Test
    public void testPersistAndLoadReview(){
        // Create and save a Customer entity
        Customer customer = new Customer(null,"Moe23","Mohamed","m.mashmoushi22@gmail.com","passowrd");
        customerRepository.save(customer);

        // Create and save a Review entity associated with the Customer
        Review review=new Review(12,"Excellen",Date.valueOf("2024-10-10"),customer);
        reviewRepository.save(review);

        // Retrieve the Review entity from the repository by its ID
        int id = review.getId();
        Review reviewFromDb = reviewRepository.findReviewById(id);

        // Validate that the retrieved Review is not null and matches the saved Review
        assertNotNull(reviewFromDb);
        assertEquals(reviewFromDb.getId(),review.getId());
        assertEquals(reviewFromDb.getReviewWriter().getEmail(),review.getReviewWriter().getEmail());
        assertEquals(reviewFromDb.getReviewDate(),review.getReviewDate());
        assertEquals(reviewFromDb.getComments(),review.getComments());
        assertEquals(reviewFromDb.getRating(),review.getRating());

    }
}