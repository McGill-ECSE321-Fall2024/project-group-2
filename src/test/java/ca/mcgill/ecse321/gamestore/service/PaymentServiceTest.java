package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    // Constants for testing purposes
    private static final Date VALID_DATE = Date.valueOf("2023-10-01");
    private static final double VALID_TOTAL = 100.0;
    private static final String VALID_DETAILS = "Payment for order #123";

    // Setup mocks with lenient behavior before each test
    @BeforeEach
    public void setUpMocks() {
        // Mock save method to return the payment object passed as an argument
        lenient().when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    // Test for successful payment creation
    @Test
    public void testCreatePayment_Success() {
        Payment payment = new Payment(VALID_DATE, VALID_TOTAL, VALID_DETAILS);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

        Payment createdPayment = paymentService.createPayment(VALID_DATE, VALID_TOTAL, VALID_DETAILS);
        assertNotNull(createdPayment);
        assertEquals(VALID_TOTAL, createdPayment.getTotal());
        assertEquals(VALID_DATE, createdPayment.getPaidDate());
        assertEquals(VALID_DETAILS, createdPayment.getDetails());
    }

    // Test for payment creation with a null date
    @Test
    public void testCreatePayment_InvalidDate() {
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                paymentService.createPayment(null, VALID_TOTAL, VALID_DETAILS));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Payment date cannot be null.", exception.getMessage());
    }

    // Test for payment creation with a negative total amount
    @Test
    public void testCreatePayment_InvalidTotal() {
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                paymentService.createPayment(VALID_DATE, -10, VALID_DETAILS));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Payment total must be greater than zero.", exception.getMessage());
    }

    // Test for payment creation with empty details
    @Test
    public void testCreatePayment_InvalidDetails() {
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                paymentService.createPayment(VALID_DATE, VALID_TOTAL, " "));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("Payment details cannot be empty.", exception.getMessage());
    }

    // Test retrieving a payment by a valid ID
    @Test
    public void testGetPaymentById_Success() {
        Payment payment = new Payment(VALID_DATE, VALID_TOTAL, VALID_DETAILS);
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(payment);

        Payment foundPayment = paymentService.getPaymentById(1);
        assertNotNull(foundPayment);
        assertEquals(VALID_TOTAL, foundPayment.getTotal());
        assertEquals(VALID_DATE, foundPayment.getPaidDate());
        assertEquals(VALID_DETAILS, foundPayment.getDetails());
    }

    // Test retrieving a payment by an ID that does not exist
    @Test
    public void testGetPaymentById_NotFound() {
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                paymentService.getPaymentById(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No payment found with ID: 999", exception.getMessage());
    }

    // Test retrieving all payments
    @Test
    public void testGetAllPayments() {
        Payment payment1 = new Payment(VALID_DATE, 100, VALID_DETAILS);
        Payment payment2 = new Payment(VALID_DATE, 200, "Another payment");
        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(2, payments.size());
    }

    // Test updating an existing payment
    @Test
    public void testUpdatePayment_Success() {
        Payment payment = new Payment(VALID_DATE, VALID_TOTAL, VALID_DETAILS);
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(payment);

        Payment updatedPayment = paymentService.updatePayment(1, Date.valueOf("2023-12-01"), 200, "Updated details");
        assertNotNull(updatedPayment);
        assertEquals(200, updatedPayment.getTotal());
        assertEquals(Date.valueOf("2023-12-01"), updatedPayment.getPaidDate());
        assertEquals("Updated details", updatedPayment.getDetails());
    }

    // Test updating a payment with an ID that does not exist
    @Test
    public void testUpdatePayment_NotFound() {
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                paymentService.updatePayment(999, VALID_DATE, VALID_TOTAL, VALID_DETAILS));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No payment found with ID: 999", exception.getMessage());
    }

    // Test deleting an existing payment
    @Test
    public void testDeletePayment_Success() {
        Payment payment = new Payment(VALID_DATE, VALID_TOTAL, VALID_DETAILS);
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(payment);

        paymentService.deletePayment(1);
        verify(paymentRepository, times(1)).delete(payment);
    }

    // Test deleting a payment with an ID that does not exist
    @Test
    public void testDeletePayment_NotFound() {
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                paymentService.deletePayment(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("No payment found with ID: 999", exception.getMessage());
    }
}



