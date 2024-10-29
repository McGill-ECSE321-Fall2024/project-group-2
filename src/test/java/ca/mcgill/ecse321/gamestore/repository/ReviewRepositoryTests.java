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
    @Autowired
    ProductRepository productRepository;
    @Autowired
    OwnerRepository ownerRepository;
    @Autowired
    InventoryRepository inventoryRepository;
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
        reviewRepository.deleteAll();
        customerRepository.deleteAll();
        productRepository.deleteAll();
        lineItemRepository.deleteAll();
        wishListRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        orderRepository.deleteAll();
        paymentRepository.deleteAll();
        ownerRepository.deleteAll();
        inventoryRepository.deleteAll();
    }


    /**
     * Tests that a Review entity can be saved and retrieved from the database.
     * It validates the fields of the retrieved Review against the saved Review.
     */
    @Test
    public void testPersistAndLoadReview(){
        Payment payment= new Payment(Date.valueOf("2024-11-22"),12,"Detail");
        paymentRepository.save(payment);

        // Create and save a Order entity
        Order order = new Order(12,Date.valueOf("2024-10-12"),Date.valueOf("2024-10-14"),"3777 rue saint-urbain",100, Order.OrderStatus.Cancelled,payment);
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
        category.setNumberItems(numberOfItems);
        categoryRepository.save(category);

        // Create and save a Product entity
        Product product= new Product("Game","Play",lineItem,category);
        productRepository.save(product);
        // Create and save a Customer entity
        Customer customer = new Customer(null,"Moe23","Mohamed","m.mashmoushi22@gmail.com","passowrd");
        customerRepository.save(customer);

        // Set up a new Inventory entity with a specific number of items
        Inventory inventory = new Inventory(13);
        inventoryRepository.save(inventory);

        // Create and save an Owner entity linked to the Inventory and ChangeRequest
        Owner owner = new Owner (null,"moe12","moe","moe@mail.com","123",inventory);
        ownerRepository.save(owner);

        // Create and save a Review entity associated with the Customer and Product
        Review review=new Review(12,"Excellen",Date.valueOf("2024-10-10"),customer,product);
        reviewRepository.save(review);

        // Retrieve the Review entity from the repository by its ID
        int id = review.getId();
        Review reviewFromDb = reviewRepository.findReviewById(id);

        // Validate that the retrieved Review is not null and matches the saved Review
        assertNotNull(reviewFromDb);
        assertEquals(reviewFromDb.getId(),review.getId());
        assertEquals(reviewFromDb.getReviewWriter().getEmail(),review.getReviewWriter().getEmail());
        assertEquals(reviewFromDb.getProduct().getId(),review.getProduct().getId());
        assertEquals(reviewFromDb.getReviewDate(),review.getReviewDate());
        assertEquals(reviewFromDb.getComments(),review.getComments());
        assertEquals(reviewFromDb.getRating(),review.getRating());

    }
}