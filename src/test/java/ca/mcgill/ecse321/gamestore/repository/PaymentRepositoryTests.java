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

//Import Date used for Date data type
import java.sql.Date;


/**
 * This class contains unit tests for the PaymentRepository to ensure that
 * Payment entities are correctly persisted and retrieved from the database.
 */
@SpringBootTest
class PaymentRepositoryApplicationTests {

    // Autowired dependency for interacting with the Payment repository
    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Clears the database before and after each test to ensure a clean environment.
     * This method deletes all Payment entities.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        paymentRepository.deleteAll();
    }

    /**
     * Tests that a Payment entity can be saved and retrieved from the database.
     * It validates the fields of the retrieved Payment against the saved Payment.
     */
    @Test
    public void testPersistAndLoadPayment(){
        // Create and save a Payment entity
        Payment payment= new Payment(Date.valueOf("2024-11-22"),12,"Detail");
        paymentRepository.save(payment);

        // Retrieve the Payment entity from the repository by its ID
        int id = payment.getId();
        Payment paymentFromDb = paymentRepository.findPaymentById(id);

        // Validate that the retrieved Payment is not null and matches the saved Payment
        assertNotNull(paymentFromDb);
        assertEquals(paymentFromDb.getId(),payment.getId());
        assertEquals(paymentFromDb.getTotal(),payment.getTotal());
        assertEquals(paymentFromDb.getDetails(),payment.getDetails());
        assertEquals(paymentFromDb.getPaidDate(),payment.getPaidDate());

    }

}