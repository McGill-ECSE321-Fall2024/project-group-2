package ca.mcgill.ecse321.gamestore.repository;

// Import necessary assertions for test validation
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//import gamestore model
import ca.mcgill.ecse321.gamestore.model.*;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//import Date used for Date data type
import java.sql.Date;

/**
 * This class contains unit tests for the OrderRepository to ensure that
 * Order entities are correctly persisted and retrieved from the database.
 */
@SpringBootTest
class OrderRepositoryApplicationTests {

    // Autowired dependencies for interacting with repositories
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;

    /**
     * Clears the database before and after each test to ensure a fresh environment.
     * This method deletes all Order and Payment entities.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        orderRepository.deleteAll();
        paymentRepository.deleteAll();
    }

    /**
     * Tests that an Order entity, along with its associated Payment, can be saved and retrieved
     * from the database. It also validates that the fields and associations are correctly stored.
     */
    @Test
    public void testPersistAndLoadOrder(){
        // Create and save a Payment entity
        Payment payment= new Payment(Date.valueOf("2024-11-22"),12,"Detail");
        paymentRepository.save(payment);

        // Create and save an Order entity, linking it to the Payment
        Order order = new Order(12,Date.valueOf("2024-10-12"),Date.valueOf("2024-10-14"),"3777 rue saint-urbain",100, OrderStatus.Cancelled,payment);
        orderRepository.save(order);

        // Retrieve the Order entity from the repository by its ID
        int id = order.getId();
        Order orderFromDb = orderRepository.findOrderById(id);

        // Validate that the retrieved Order is not null and matches the saved Order and its associated Payment
        assertNotNull(orderFromDb);
        assertEquals(order.getId(),orderFromDb.getId());
        assertEquals(orderFromDb.getPaymentOfOrder().getId(), order.getPaymentOfOrder().getId());
        assertEquals(orderFromDb.getOrderedDate(),order.getOrderedDate());
        assertEquals(orderFromDb.getNumber(),order.getNumber());
        assertEquals(orderFromDb.getShipTo(),order.getShipTo());
        assertEquals(orderFromDb.getShippedDate(),order.getShippedDate());
        assertEquals(orderFromDb.getStatus(),order.getStatus());

    }

}