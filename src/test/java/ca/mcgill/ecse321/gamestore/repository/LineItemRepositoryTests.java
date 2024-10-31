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

    /**
     * Tests that a LineItem entity, along with its associated Order and ShoppingCart,
     * can be saved and retrieved from the database. It also validates associations with
     * the Payment entity of the Order.
     */
    @Test
    public void testPersistAndLoadLineItem(){
        // Create and save a Payment entity
        Payment payment= new Payment(Date.valueOf("2024-11-22"),16,"Detail");
        paymentRepository.save(payment);

        // Create and save an Order entity, linking it to the Payment
        Order order = new Order(15,Date.valueOf("2024-10-12"),Date.valueOf("2024-10-14"),"3777 rue saint-urbain",100, OrderStatus.Cancelled,payment);
        orderRepository.save(order);

        // Create and save a ShoppingCart entity
        ShoppingCart shoppingCart= new ShoppingCart(Date.valueOf("2024-10-09"));
        shoppingCartRepository.save(shoppingCart);

        // Create and save a LineItem entity, associating it with the Order and ShoppingCart
        LineItem lineItem = new LineItem(13,12,order,shoppingCart);
        lineItemRepository.save(lineItem);

        // Retrieve the LineItem entity from the repository by its ID
        int id = lineItem.getId();
        LineItem lineItemFromDb = lineItemRepository.findLineItemById(id);

        // Validate that the retrieved LineItem is not null and matches the saved LineItem and its associations
        assertNotNull(lineItemFromDb);
        assertEquals(lineItemFromDb.getOrder().getId(),order.getId());
        assertEquals(lineItemFromDb.getOrder().getPaymentOfOrder().getId(),payment.getId());
        assertEquals(lineItemFromDb.getCart().getId(), shoppingCart.getId());
        assertEquals(lineItemFromDb.getPrice(),lineItem.getPrice());
        assertEquals(lineItemFromDb.getQuantity(),lineItem.getQuantity());
    }
}