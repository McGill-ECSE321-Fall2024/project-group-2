package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    private static final Date VALID_DATE = Date.valueOf("2023-10-01");
    private static final double VALID_TOTAL = 100.0;
    private static final String VALID_DETAILS = "Payment for order #123";

    @BeforeEach
    public void setUpMocks() {
        lenient().when(paymentRepository.save(any(Payment.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

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

    @Test
    public void testCreatePayment_InvalidDate() {
        Exception exception = assertThrows(ResponseStatusException.class, () ->
                paymentService.createPayment(null, VALID_TOTAL, VALID_DETAILS));
        assertEquals("400 BAD_REQUEST \"Payment date cannot be null.\"", exception.getMessage());
    }

    @Test
    public void testCreatePayment_InvalidTotal() {
        Exception exception = assertThrows(ResponseStatusException.class, () ->
                paymentService.createPayment(VALID_DATE, -10, VALID_DETAILS));
        assertEquals("400 BAD_REQUEST \"Payment total must be greater than zero.\"", exception.getMessage());
    }

    @Test
    public void testCreatePayment_InvalidDetails() {
        Exception exception = assertThrows(ResponseStatusException.class, () ->
                paymentService.createPayment(VALID_DATE, VALID_TOTAL, " "));
        assertEquals("400 BAD_REQUEST \"Payment details cannot be empty.\"", exception.getMessage());
    }

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

    @Test
    public void testGetPaymentById_NotFound() {
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                paymentService.getPaymentById(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No payment found with ID: 999", exception.getReason());
    }

    @Test
    public void testGetAllPayments() {
        Payment payment1 = new Payment(VALID_DATE, 100, VALID_DETAILS);
        Payment payment2 = new Payment(VALID_DATE, 200, "Another payment");
        when(paymentRepository.findAll()).thenReturn(List.of(payment1, payment2));

        List<Payment> payments = paymentService.getAllPayments();
        assertEquals(2, payments.size());
    }

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

    @Test
    public void testUpdatePayment_NotFound() {
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                paymentService.updatePayment(999, VALID_DATE, VALID_TOTAL, VALID_DETAILS));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No payment found with ID: 999", exception.getReason());
    }

    @Test
    public void testDeletePayment_Success() {
        Payment payment = new Payment(VALID_DATE, VALID_TOTAL, VALID_DETAILS);
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(payment);

        paymentService.deletePayment(1);
        verify(paymentRepository, times(1)).delete(payment);
    }

    @Test
    public void testDeletePayment_NotFound() {
        when(paymentRepository.findPaymentById(anyInt())).thenReturn(null);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () ->
                paymentService.deletePayment(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("No payment found with ID: 999", exception.getReason());
    }
}

