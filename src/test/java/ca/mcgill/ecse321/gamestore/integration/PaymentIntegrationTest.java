package ca.mcgill.ecse321.gamestore.integration;

import ca.mcgill.ecse321.gamestore.dto.PaymentRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentResponseDto;
import ca.mcgill.ecse321.gamestore.repository.PaymentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PaymentIntegrationTest {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    // Clear the database before and after each test
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        paymentRepository.deleteAll();
    }

    // Set the timezone to UTC before all tests to avoid date discrepancies
    @BeforeAll
    public static void setUpTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    /**
     * Test for creating a payment successfully.
     */
    @Test
    public void testCreatePayment_Success() {
        LocalDate paidDate = LocalDate.of(2023, 10, 10);
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf(paidDate), 100.0, "Order #123");

        ResponseEntity<PaymentResponseDto> response = restTemplate.postForEntity("/api/payment/create", request, PaymentResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        PaymentResponseDto createdPayment = response.getBody();
        assertNotNull(createdPayment);

        assertEquals(paidDate, createdPayment.getPaidDate().toLocalDate());
        assertEquals(100.0, createdPayment.getTotal());
        assertEquals("Order #123", createdPayment.getDetails());
    }

    /**
     * Test for creating a payment with an invalid date (null).
     */
    @Test
    public void testCreatePayment_InvalidDate() {
        PaymentRequestDto request = new PaymentRequestDto(null, 100.0, "Order #124");
        ResponseEntity<String> response = restTemplate.postForEntity("/api/payment/create", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Payment date cannot be null.", response.getBody());
    }

    /**
     * Test for creating a payment with a negative total.
     */
    @Test
    public void testCreatePayment_InvalidTotal() {
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf("2023-10-10"), -100.0, "Order #125");
        ResponseEntity<String> response = restTemplate.postForEntity("/api/payment/create", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Payment total must be greater than zero.", response.getBody());
    }

    /**
     * Test for creating a payment with empty details.
     */
    @Test
    public void testCreatePayment_InvalidDetails() {
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf("2023-10-10"), 100.0, " ");
        ResponseEntity<String> response = restTemplate.postForEntity("/api/payment/create", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Payment details cannot be empty.", response.getBody());
    }

    /**
     * Test for retrieving a payment by ID.
     */
    @Test
    public void testGetPaymentById() {
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf("2023-10-10"), 100.0, "Order #126");
        ResponseEntity<PaymentResponseDto> postResponse = restTemplate.postForEntity("/api/payment/create", request, PaymentResponseDto.class);

        assertNotNull(postResponse);
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode());
        PaymentResponseDto createdPayment = postResponse.getBody();
        assertNotNull(createdPayment);
        assertNotNull(createdPayment.getPaymentId());

        ResponseEntity<PaymentResponseDto> getResponse = restTemplate.getForEntity("/api/payment/" + createdPayment.getPaymentId(), PaymentResponseDto.class);

        assertNotNull(getResponse);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
        PaymentResponseDto retrievedPayment = getResponse.getBody();
        assertNotNull(retrievedPayment);
        assertEquals(createdPayment.getPaymentId(), retrievedPayment.getPaymentId());
    }

    /**
     * Test for retrieving a payment by an invalid ID.
     */
    @Test
    public void testGetPaymentById_NotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/payment/999", String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No payment found with ID: 999", response.getBody());
    }

    /**
     * Test for retrieving all payments.
     */
    @Test
    public void testGetAllPayments() {
        PaymentRequestDto request1 = new PaymentRequestDto(Date.valueOf("2023-10-10"), 100.0, "Order #127");
        PaymentRequestDto request2 = new PaymentRequestDto(Date.valueOf("2023-10-11"), 200.0, "Order #128");
        restTemplate.postForEntity("/api/payment/create", request1, PaymentResponseDto.class);
        restTemplate.postForEntity("/api/payment/create", request2, PaymentResponseDto.class);

        ResponseEntity<PaymentResponseDto[]> response = restTemplate.getForEntity("/api/payment/all", PaymentResponseDto[].class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PaymentResponseDto[] payments = response.getBody();
        assertNotNull(payments);
        assertEquals(2, payments.length);
    }

    /**
     * Test for updating a payment successfully.
     */
    @Test
    public void testUpdatePayment_Success() {
        LocalDate initialDate = LocalDate.of(2023, 10, 10);
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf(initialDate), 100.0, "Order #123");
        ResponseEntity<PaymentResponseDto> response = restTemplate.postForEntity("/api/payment/create", request, PaymentResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PaymentResponseDto createdPayment = response.getBody();
        assertNotNull(createdPayment);

        LocalDate newDate = LocalDate.of(2023, 12, 1);
        PaymentRequestDto updateRequest = new PaymentRequestDto(Date.valueOf(newDate), 200.0, "Updated Order #123");
        ResponseEntity<PaymentResponseDto> updateResponse = restTemplate.exchange(
                "/api/payment/update/" + createdPayment.getPaymentId(),
                HttpMethod.PUT,
                new HttpEntity<>(updateRequest),
                PaymentResponseDto.class
        );

        assertNotNull(updateResponse);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

        PaymentResponseDto updatedPayment = updateResponse.getBody();
        assertNotNull(updatedPayment);
        assertEquals(newDate, updatedPayment.getPaidDate().toLocalDate());
        assertEquals(200.0, updatedPayment.getTotal());
        assertEquals("Updated Order #123", updatedPayment.getDetails());
    }

    /**
     * Test for updating a payment with an invalid ID.
     */
    @Test
    public void testUpdatePayment_NotFound() {
        PaymentRequestDto updatedRequest = new PaymentRequestDto(Date.valueOf("2023-12-01"), 150.0, "Non-existing Order");
        ResponseEntity<String> response = restTemplate.exchange("/api/payment/update/999", HttpMethod.PUT, new HttpEntity<>(updatedRequest), String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No payment found with ID: 999", response.getBody());
    }

    /**
     * Test for deleting a payment by ID.
     */
    @Test
    public void testDeletePayment_Success() {
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf("2023-10-10"), 100.0, "Order #130");
        PaymentResponseDto createdPayment = restTemplate.postForEntity("/api/payment/create", request, PaymentResponseDto.class).getBody();
        assertNotNull(createdPayment);

        ResponseEntity<Void> response = restTemplate.exchange("/api/payment/delete/" + createdPayment.getPaymentId(), HttpMethod.DELETE, null, Void.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/payment/" + createdPayment.getPaymentId(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertEquals("No payment found with ID: " + createdPayment.getPaymentId(), getResponse.getBody());
    }

    /**
     * Test for deleting a payment with an invalid ID.
     */
    @Test
    public void testDeletePayment_NotFound() {
        ResponseEntity<String> response = restTemplate.exchange("/api/payment/delete/999", HttpMethod.DELETE, null, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No payment found with ID: 999", response.getBody());
    }
}


