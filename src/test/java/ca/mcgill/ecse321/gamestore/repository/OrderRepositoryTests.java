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
class OrderRepositoryApplicationTests {


    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository;




    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        orderRepository.deleteAll();
        paymentRepository.deleteAll();





    }

    @Test
    public void testPersistAndLoadOrder(){
        Payment payment= new Payment(Date.valueOf("2024-11-22"),12,"Detail");
        paymentRepository.save(payment);
        Order order = new Order(12,Date.valueOf("2024-10-12"),Date.valueOf("2024-10-14"),"3777 rue saint-urbain",100, OrderStatus.Cancelled,payment);
        orderRepository.save(order);

        int id = order.getId();
        Order orderFromDb = orderRepository.findOrderById(id);




        // Read Category from database
        assertNotNull(orderFromDb);
        assertEquals(orderFromDb.getPaymentOfOrder().getId(), order.getPaymentOfOrder().getId());
        assertEquals(order.getId(),orderFromDb.getId());
        assertEquals(orderFromDb.getOrderedDate(),order.getOrderedDate());
        assertEquals(orderFromDb.getNumber(),order.getNumber());
        assertEquals(orderFromDb.getShipTo(),order.getShipTo());
        assertEquals(orderFromDb.getShippedDate(),order.getShippedDate());
        assertEquals(orderFromDb.getStatus(),order.getStatus());

    }




}