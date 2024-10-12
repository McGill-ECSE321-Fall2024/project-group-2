package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import ca.mcgill.ecse321.gamestore.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

import java.sql.Date;

@SpringBootTest
class ProductRepositoryApplicationTests {



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


    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        productRepository.deleteAll();
        lineItemRepository.deleteAll();
        wishListRepository.deleteAll();
        shoppingCartRepository.deleteAll();
        orderRepository.deleteAll();
        paymentRepository.deleteAll();


    }

    @Test
    public void testPersistAndLoadProduct(){
        Payment payment= new Payment(Date.valueOf("2024-11-22"),12,"Detail");
        paymentRepository.save(payment);
        Order order = new Order(12,Date.valueOf("2024-10-12"),Date.valueOf("2024-10-14"),"3777 rue saint-urbain",100, OrderStatus.Cancelled,payment);
        orderRepository.save(order);
        ShoppingCart shoppingCart= new ShoppingCart(Date.valueOf("2024-10-09"));
        shoppingCartRepository.save(shoppingCart);
        LineItem lineItem = new LineItem(14,12,order,shoppingCart);
        lineItemRepository.save(lineItem);
        String name="Mohamed";
        int numberOfItems=12;
        Category category=new Category();
        category.setName(name);
        category.setNumberItems(numberOfItems);
        categoryRepository.save(category);
        Product product= new Product("Game","Play",lineItem,category);
        productRepository.save(product);
        int id = product.getId();
        Product productFromDb = productRepository.findProductById(id);




        assertNotNull(productFromDb);
        assertEquals(productFromDb.getId(),product.getId());

        assertEquals(productFromDb.getCategory().getId(),product.getCategory().getId());
        assertEquals(productFromDb.getName(),product.getName());
        assertEquals(productFromDb.getLineItemOfProduct().getId(),product.getLineItemOfProduct().getId());
        assertEquals(productFromDb.getDescription(),product.getDescription());

    }




}