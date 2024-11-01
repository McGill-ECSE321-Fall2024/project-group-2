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
 * This class contains unit tests for the LineItemRepository to ensure that
 * LineItem entities are correctly persisted and retrieved from the database.
 */
@SpringBootTest
class LineItemRepositoryApplicationTests {

    // Autowired dependencies for interacting with repositories
    @Autowired
    private LineItemRepository lineItemRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private WishListRepository wishListRepository;

    /**
     * Clears the database before and after each test to ensure a fresh environment.
     * This method deletes all LineItem, Order, Payment, ShoppingCart, and WishList entities.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        lineItemRepository.deleteAll();
        orderRepository.deleteAll();
        paymentRepository.deleteAll();
        wishListRepository.deleteAll();
        shoppingCartRepository.deleteAll();
    }


}