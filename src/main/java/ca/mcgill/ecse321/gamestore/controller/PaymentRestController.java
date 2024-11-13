package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.PaymentRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentResponseDto;
import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for handling /api/payments endpoint requests.
 */
@CrossOrigin(origins = "*")
@RestController
public class PaymentRestController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Create a new payment.
     *
     * @param paymentRequestDto The payment details.
     * @return ResponseEntity containing the created payment as PaymentResponseDto and HTTP status.
     */
    @PostMapping("/api/payment/")
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentService.createPayment(
                paymentRequestDto.getPaidDate(),
                paymentRequestDto.getTotal(),
                paymentRequestDto.getDetails());
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto(
                payment.getId(),
                payment.getPaidDate(),
                payment.getTotal(),
                payment.getDetails());
        return new ResponseEntity<>(paymentResponseDto, HttpStatus.CREATED);
    }

    /**
     * Get all payments.
     *
     * @return ResponseEntity containing a list of all payments as List<PaymentResponseDto> and HTTP status.
     */
    @GetMapping("/api/payment/all")
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        List<PaymentResponseDto> payments = paymentService.getAllPayments().stream()
                .map(payment -> new PaymentResponseDto(
                        payment.getId(),
                        payment.getPaidDate(),
                        payment.getTotal(),
                        payment.getDetails()))
                .collect(Collectors.toList());
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    /**
     * Get a payment by ID.
     *
     * @param id The payment ID.
     * @return ResponseEntity containing the payment with the specified ID as PaymentResponseDto and HTTP status.
     */
    @GetMapping("/api/payment/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable("id") int id) {
        Payment payment = paymentService.getPaymentById(id);
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto(
                payment.getId(),
                payment.getPaidDate(),
                payment.getTotal(),
                payment.getDetails());
        return new ResponseEntity<>(paymentResponseDto, HttpStatus.OK);
    }

    /**
     * Update an existing payment.
     *
     * @param id                The payment ID.
     * @param paymentRequestDto The updated payment details.
     * @return ResponseEntity containing the updated payment as PaymentResponseDto and HTTP status.
     */
    @PutMapping("/api/payment/{id}")
    public ResponseEntity<PaymentResponseDto> updatePayment(@PathVariable("id") int id, @RequestBody PaymentRequestDto paymentRequestDto) {
        Payment updatedPayment = paymentService.updatePayment(
                id,
                paymentRequestDto.getPaidDate(),
                paymentRequestDto.getTotal(),
                paymentRequestDto.getDetails());
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto(
                updatedPayment.getId(),
                updatedPayment.getPaidDate(),
                updatedPayment.getTotal(),
                updatedPayment.getDetails());
        return new ResponseEntity<>(paymentResponseDto, HttpStatus.OK);
    }

    /**
     * Delete a payment by ID.
     *
     * @param id The payment ID.
     * @return ResponseEntity with HTTP status indicating the outcome of the delete operation.
     */
    @DeleteMapping("/api/payment/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable("id") int id) {
        paymentService.deletePayment(id);
        return ResponseEntity.noContent().build();
    }
}




