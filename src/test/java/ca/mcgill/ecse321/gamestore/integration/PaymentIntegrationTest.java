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
        // Use LocalDate for date comparison
        LocalDate paidDate = LocalDate.of(2023, 10, 10);
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf(paidDate), 100.0, "Order #123");

        ResponseEntity<PaymentResponseDto> response = restTemplate.postForEntity("/api/payments", request, PaymentResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        PaymentResponseDto createdPayment = response.getBody();
        assertNotNull(createdPayment);

        // Convert SQL Date to LocalDate for comparison
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
        ResponseEntity<String> response = restTemplate.postForEntity("/api/payments", request, String.class);

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
        ResponseEntity<String> response = restTemplate.postForEntity("/api/payments", request, String.class);

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
        ResponseEntity<String> response = restTemplate.postForEntity("/api/payments", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Payment details cannot be empty.", response.getBody());
    }

    /**
     * Test for retrieving a payment by ID.
     */
    @Test
    public void testGetPaymentById() {
        // Create a new payment using a POST request
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf("2023-10-10"), 100.0, "Order #126");
        ResponseEntity<PaymentResponseDto> postResponse = restTemplate.postForEntity("/api/payments", request, PaymentResponseDto.class);

        // Check if the post response and its body are not null
        assertNotNull(postResponse, "POST response should not be null");
        assertEquals(HttpStatus.CREATED, postResponse.getStatusCode(), "Expected HTTP Status 201 CREATED");
        PaymentResponseDto createdPayment = postResponse.getBody();
        assertNotNull(createdPayment, "Created payment should not be null");
        assertNotNull(createdPayment.getPaymentId(), "Created payment ID should not be null");

        // Use the ID from the created payment to fetch it back using GET
        ResponseEntity<PaymentResponseDto> getResponse = restTemplate.getForEntity("/api/payments/" + createdPayment.getPaymentId(), PaymentResponseDto.class);

        // Check if the GET response and its body are valid
        assertNotNull(getResponse, "GET response should not be null");
        assertEquals(HttpStatus.OK, getResponse.getStatusCode(), "Expected HTTP Status 200 OK");
        PaymentResponseDto retrievedPayment = getResponse.getBody();
        assertNotNull(retrievedPayment, "Retrieved payment should not be null");
        assertEquals(createdPayment.getPaymentId(), retrievedPayment.getPaymentId(), "Payment ID should match the created payment ID");
    }

    /**
     * Test for retrieving a payment by an invalid ID.
     */
    @Test
    public void testGetPaymentById_NotFound() {
        ResponseEntity<String> response = restTemplate.getForEntity("/api/payments/999", String.class);

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
        restTemplate.postForEntity("/api/payments", request1, PaymentResponseDto.class);
        restTemplate.postForEntity("/api/payments", request2, PaymentResponseDto.class);

        ResponseEntity<PaymentResponseDto[]> response = restTemplate.getForEntity("/api/payments", PaymentResponseDto[].class);

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
        // Initial payment creation
        LocalDate initialDate = LocalDate.of(2023, 10, 10);
        PaymentRequestDto request = new PaymentRequestDto(Date.valueOf(initialDate), 100.0, "Order #123");
        ResponseEntity<PaymentResponseDto> response = restTemplate.postForEntity("/api/payments", request, PaymentResponseDto.class);

        // Assert creation was successful
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PaymentResponseDto createdPayment = response.getBody();
        assertNotNull(createdPayment);

        // Update payment with new date and details
        LocalDate newDate = LocalDate.of(2023, 12, 1);  // Ensure this date is correctly set
        PaymentRequestDto updateRequest = new PaymentRequestDto(Date.valueOf(newDate), 200.0, "Updated Order #123");
        ResponseEntity<PaymentResponseDto> updateResponse = restTemplate.exchange(
                "/api/payments/" + createdPayment.getPaymentId(),
                HttpMethod.PUT,
                new HttpEntity<>(updateRequest),
                PaymentResponseDto.class
        );

        // Assert update was successful
        assertNotNull(updateResponse);
        assertEquals(HttpStatus.OK, updateResponse.getStatusCode());

        PaymentResponseDto updatedPayment = updateResponse.getBody();
        assertNotNull(updatedPayment);

        // Use LocalDate for assertions to avoid timezone issues
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
        ResponseEntity<String> response = restTemplate.exchange("/api/payments/999", HttpMethod.PUT, new HttpEntity<>(updatedRequest), String.class);

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
        PaymentResponseDto createdPayment = restTemplate.postForEntity("/api/payments", request, PaymentResponseDto.class).getBody();
        assertNotNull(createdPayment);

        ResponseEntity<Void> response = restTemplate.exchange("/api/payments/" + createdPayment.getPaymentId(), HttpMethod.DELETE, null, Void.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        // Verify deletion
        ResponseEntity<String> getResponse = restTemplate.getForEntity("/api/payments/" + createdPayment.getPaymentId(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertEquals("No payment found with ID: " + createdPayment.getPaymentId(), getResponse.getBody());
    }

    /**
     * Test for deleting a payment with an invalid ID.
     */
    @Test
    public void testDeletePayment_NotFound() {
        ResponseEntity<String> response = restTemplate.exchange("/api/payments/999", HttpMethod.DELETE, null, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("No payment found with ID: 999", response.getBody());
    }
}

