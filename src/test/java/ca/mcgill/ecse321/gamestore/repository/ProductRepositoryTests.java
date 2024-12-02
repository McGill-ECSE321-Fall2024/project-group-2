package ca.mcgill.ecse321.gamestore.repository;

// Import necessary assertions for test validation
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

//Import gamestore model
import ca.mcgill.ecse321.gamestore.model.*;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

//Import Date used for Date data type
import java.sql.Date;

/**
 * This class contains unit tests for the ProductRepository to ensure that
 * Product entities are correctly persisted and retrieved from the database.
 */
@SpringBootTest
class ProductRepositoryApplicationTests {

    // Autowired dependencies for interacting with various repositories
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private LineItemRepository lineItemRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderRepository orderRepository;


    /**
     * Clears the database before and after each test to ensure a clean environment.
     * This method deletes all relevant entities from the repositories.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        productRepository.deleteAll();
        lineItemRepository.deleteAll();
        wishListRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        orderRepository.deleteAll();
        paymentRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    /**
     * Tests that a Product entity can be saved and retrieved from the database.
     * It validates the fields of the retrieved Product against the saved Product.
     */
    @Test
    public void testPersistAndLoadProduct(){
        // Create and save a Payment entity
        Payment payment= new Payment(Date.valueOf("2024-11-22"),12,"Detail");
        paymentRepository.save(payment);

        // Create and save a Order entity
        Order order = new Order(12,Date.valueOf("2024-10-12"),Date.valueOf("2024-10-14"),"3777 rue saint-urbain",100, OrderStatus.Cancelled,payment);
        orderRepository.save(order);

        // Create and save a shoppingCart entity
        ShoppingCart shoppingCart= new ShoppingCart(Date.valueOf("2024-10-09"));
        shoppingCartRepository.save(shoppingCart);

        // Create and save a LineItem entity
        LineItem lineItem = new LineItem(14,12,order,shoppingCart);
        lineItemRepository.save(lineItem);

        // Create and save a Category entity
        String name="Mohamed";
        int numberOfItems=12;
        Category category=new Category();
        category.setName(name);
        category.setName("cat1");
        categoryRepository.save(category);

        // Create and save a Product entity
        Product product= new Product("Game","Play", "www",lineItem,category);
        productRepository.save(product);

        // Retrieve the Product entity from the repository by its ID
        int id = product.getId();
        Product productFromDb = productRepository.findProductById(id);

        // Validate that the retrieved Product is not null and matches the saved Product
        assertNotNull(productFromDb);
        assertEquals(productFromDb.getId(),product.getId());
        assertEquals(productFromDb.getCategory().getId(),product.getCategory().getId());
        assertEquals(productFromDb.getName(),product.getName());
        assertEquals(productFromDb.getLineItemOfProduct().getId(),product.getLineItemOfProduct().getId());
        assertEquals(productFromDb.getDescription(),product.getDescription());

    }
}