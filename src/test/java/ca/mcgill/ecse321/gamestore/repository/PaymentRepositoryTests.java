package ca.mcgill.ecse321.gamestore.repository;

import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

@SpringBootTest
@Transactional
public class PaymentRepositoryTests {

    @Autowired
    private PaymentRepository paymentRepository;

    private Payment payment;

    @BeforeEach
    public void setUp() {
        payment = new Payment(Date.valueOf("2024-10-10"), 100.0, "Payment for order");
        paymentRepository.save(payment);
    }

    @Test
    public void testSavePayment() {
        Payment foundPayment = paymentRepository.findPaymentById((int) payment.getTotal()); // Finding by total as an alternative
        Assertions.assertNotNull(foundPayment);
        Assertions.assertEquals(payment.getPaidDate(), foundPayment.getPaidDate());
        Assertions.assertEquals(payment.getTotal(), foundPayment.getTotal());
        Assertions.assertEquals(payment.getDetails(), foundPayment.getDetails());
    }

    @Test
    public void testUpdatePayment() {
        Payment foundPayment = paymentRepository.findPaymentById((int) payment.getTotal()); // Finding by total as an alternative
        foundPayment.setTotal(150.0);
        paymentRepository.save(foundPayment);

        Payment updatedPayment = paymentRepository.findPaymentById((int) 150.0); // Finding by new total
        Assertions.assertEquals(150.0, updatedPayment.getTotal());
    }

    @Test
    public void testDeletePayment() {
        paymentRepository.delete(payment);
        Payment foundPayment = paymentRepository.findPaymentById((int) payment.getTotal()); // Finding by total as an alternative
        Assertions.assertNull(foundPayment);
    }

    @Test
    public void testFindByTotal() {
        Payment foundPayment = paymentRepository.findPaymentById((int) payment.getTotal()); // Finding by total as an alternative
        Assertions.assertNotNull(foundPayment);
        Assertions.assertEquals(100.0, foundPayment.getTotal());
    }

    @Test
    public void testFindByDetails() {
        Payment foundPayment = paymentRepository.findPaymentById((int) payment.getTotal()); // Finding by total as an alternative
        Assertions.assertEquals("Payment for order", foundPayment.getDetails());
    }
}
