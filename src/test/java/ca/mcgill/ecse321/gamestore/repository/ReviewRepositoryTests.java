package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.*;
import ca.mcgill.ecse321.gamestore.model.Review.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

import java.sql.Date;

@SpringBootTest
class ReviewRepositoryApplicationTests {



    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        reviewRepository.deleteAll();

        customerRepository.deleteAll();



    }

    @Test
    public void testPersistAndLoadReview(){
        Customer customer = new Customer(null,"Moe23","Mohamed","m.mashmoushi22@gmail.com","passowrd");
        customerRepository.save(customer);
        Review review=new Review(12,"Excellen",Date.valueOf("2024-10-10"),customer);
        reviewRepository.save(review);
        int id = review.getId();
        Review reviewFromDb = reviewRepository.findReviewById(id);




        // Read Category from database
        assertNotNull(reviewFromDb);
        assertEquals(reviewFromDb.getId(),review.getId());
        assertEquals(reviewFromDb.getReviewWriter().getEmail(),review.getReviewWriter().getEmail());

        assertEquals(reviewFromDb.getReviewDate(),review.getReviewDate());
        assertEquals(reviewFromDb.getComments(),review.getComments());
        assertEquals(reviewFromDb.getRating(),review.getRating());

    }




}