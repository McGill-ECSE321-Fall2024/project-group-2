package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.PaymentRequestDto;
import ca.mcgill.ecse321.gamestore.dto.PaymentResponseDto;
import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {

    @Autowired
    private PaymentService paymentService;

    /**
     * Get all payments.
     *
     * @return List of all payments as PaymentResponseDto.
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentResponseDto> getAllPayments() {
        return paymentService.getAllPayments().stream()
                .map(payment -> new PaymentResponseDto(
                        payment.getId(),
                        payment.getPaidDate(),
                        payment.getTotal(),
                        payment.getDetails()))
                .collect(Collectors.toList());
    }

    /**
     * Get a payment by ID.
     *
     * @param id The payment ID.
     * @return The payment with the specified ID as PaymentResponseDto.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponseDto getPaymentById(@PathVariable int id) {
        Payment payment = paymentService.getPaymentById(id);
        return new PaymentResponseDto(
                payment.getId(),
                payment.getPaidDate(),
                payment.getTotal(),
                payment.getDetails());
    }

    /**
     * Create a new payment.
     *
     * @param paymentRequestDto The payment details.
     * @return The created payment as PaymentResponseDto.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDto createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentService.createPayment(
                paymentRequestDto.getPaidDate(),
                paymentRequestDto.getTotal(),
                paymentRequestDto.getDetails());
        return new PaymentResponseDto(
                payment.getId(),
                payment.getPaidDate(),
                payment.getTotal(),
                payment.getDetails());
    }

    /**
     * Update an existing payment.
     *
     * @param id                The payment ID.
     * @param paymentRequestDto The updated payment details.
     * @return The updated payment as PaymentResponseDto.
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentResponseDto updatePayment(@PathVariable int id, @RequestBody PaymentRequestDto paymentRequestDto) {
        Payment updatedPayment = paymentService.updatePayment(
                id,
                paymentRequestDto.getPaidDate(),
                paymentRequestDto.getTotal(),
                paymentRequestDto.getDetails());
        return new PaymentResponseDto(
                updatedPayment.getId(),
                updatedPayment.getPaidDate(),
                updatedPayment.getTotal(),
                updatedPayment.getDetails());
    }

    /**
     * Delete a payment by ID.
     *
     * @param id The payment ID.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePayment(@PathVariable int id) {
        paymentService.deletePayment(id);
    }
}
