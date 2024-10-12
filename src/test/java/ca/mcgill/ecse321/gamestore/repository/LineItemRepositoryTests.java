package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

import java.sql.Date;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;
@SpringBootTest
class LineItemRepositoryApplicationTests {


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




    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        lineItemRepository.deleteAll();

        orderRepository.deleteAll();
        paymentRepository.deleteAll();
        wishListRepository.deleteAll();
        shoppingCartRepository.deleteAll();





    }

    @Test
    public void testPersistAndLoadLineItem(){
        Payment payment= new Payment(Date.valueOf("2024-11-22"),16,"Detail");
        paymentRepository.save(payment);
        Order order = new Order(15,Date.valueOf("2024-10-12"),Date.valueOf("2024-10-14"),"3777 rue saint-urbain",100, OrderStatus.Cancelled,payment);
        orderRepository.save(order);
        ShoppingCart shoppingCart= new ShoppingCart(Date.valueOf("2024-10-09"));
        shoppingCartRepository.save(shoppingCart);
        LineItem lineItem = new LineItem(13,12,order,shoppingCart);
        lineItemRepository.save(lineItem);
        int id = lineItem.getId();
        LineItem lineItemFromDb = lineItemRepository.findLineItemById(id);




        // Read Category from database
        assertNotNull(lineItemFromDb);

        assertEquals(lineItemFromDb.getOrder().getId(),order.getId());
        assertEquals(lineItemFromDb.getOrder().getPaymentOfOrder().getId(),payment.getId());
        assertEquals(lineItemFromDb.getCart().getId(), shoppingCart.getId());
        assertEquals(lineItemFromDb.getPrice(),lineItem.getPrice());
        assertEquals(lineItemFromDb.getQuantity(),lineItem.getQuantity());

    }




}