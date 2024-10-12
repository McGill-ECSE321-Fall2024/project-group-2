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
class PaymentRepositoryApplicationTests {



    @Autowired
    private PaymentRepository paymentRepository;




    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        paymentRepository.deleteAll();





    }

    @Test
    public void testPersistAndLoadPayment(){
        Payment payment= new Payment(Date.valueOf("2024-11-22"),12,"Detail");
        paymentRepository.save(payment);
        int id = payment.getId();
        Payment paymentFromDb = paymentRepository.findPaymentById(id);




        // Read Category from database
        assertNotNull(paymentFromDb);
        assertEquals(paymentFromDb.getId(),payment.getId());
        assertEquals(paymentFromDb.getTotal(),payment.getTotal());
        assertEquals(paymentFromDb.getDetails(),payment.getDetails());
        assertEquals(paymentFromDb.getPaidDate(),payment.getPaidDate());

    }




}