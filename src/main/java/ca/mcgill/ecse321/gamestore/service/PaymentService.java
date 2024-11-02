package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.List;

/**
 * Service class for managing Payment operations.
 */
@Service
public class PaymentService {

    @Autowired
    private PaymentRepository PaymentRepository;

    /**
     * Retrieve all payments.
     *
     * @return List of all payments.
     */
    @Transactional
    public List<Payment> getAllPayments() {
        return (List<Payment>) PaymentRepository.findAll();
    }

    /**
     * Retrieve a payment by its ID.
     *
     * @param id Payment ID.
     * @return The found payment.
     * @throws ResponseStatusException if the payment is not found.
     */
    @Transactional
    public Payment getPaymentById(int id) {
        Payment payment = PaymentRepository.findPaymentById(id);
        if (payment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "No payment found with ID: " + id);
        }
        return payment;
    }

    /**
     * Create a new payment.
     *
     * @param paidDate Date of the payment.
     * @param total Total amount of the payment.
     * @param details Additional payment details.
     * @return The created payment.
     * @throws ResponseStatusException if validation fails.
     */
    @Transactional
    public Payment createPayment(Date paidDate, double total, String details) {
        validatePaymentInputs(paidDate, total, details);
        Payment payment = new Payment(paidDate, total, details);
        return PaymentRepository.save(payment);
    }

    /**
     * Update an existing payment's details.
     *
     * @param id Payment ID.
     * @param newPaidDate New payment date.
     * @param newTotal New total amount.
     * @param newDetails New payment details.
     * @return The updated payment.
     * @throws ResponseStatusException if the payment is not found or validation fails.
     */
    @Transactional
    public Payment updatePayment(int id, Date newPaidDate, double newTotal, String newDetails) {
        Payment payment = getPaymentById(id);

        validatePaymentInputs(newPaidDate, newTotal, newDetails);

        payment.setPaidDate(newPaidDate);
        payment.setTotal(newTotal);
        payment.setDetails(newDetails);

        return PaymentRepository.save(payment);
    }

    /**
     * Delete a payment by its ID.
     *
     * @param id Payment ID.
     * @throws ResponseStatusException if the payment is not found.
     */
    @Transactional
    public void deletePayment(int id) {
        Payment payment = getPaymentById(id);
        PaymentRepository.delete(payment);
    }

    /**
     * Validate payment input fields.
     *
     * @param paidDate Date of the payment.
     * @param total Total amount of the payment.
     * @param details Payment details.
     * @throws ResponseStatusException if validation fails.
     */
    private void validatePaymentInputs(Date paidDate, double total, String details) {
        if (paidDate == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Payment date cannot be null.");
        }
        if (total <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Payment total must be greater than zero.");
        }
        if (details == null || details.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Payment details cannot be empty.");
        }
    }
}
