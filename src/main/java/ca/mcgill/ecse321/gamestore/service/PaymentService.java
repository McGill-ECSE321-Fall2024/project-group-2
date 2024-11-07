package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public List<Payment> getAllPayments() {
        return (List<Payment>) paymentRepository.findAll();
    }

    @Transactional
    public Payment getPaymentById(int id) {
        Payment payment = paymentRepository.findPaymentById(id);
        if (payment == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "No payment found with ID: " + id);
        }
        return payment;
    }

    @Transactional
    public Payment createPayment(Date paidDate, double total, String details) {
        validatePaymentInputs(paidDate, total, details);
        Payment payment = new Payment(paidDate, total, details);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment updatePayment(int id, Date newPaidDate, double newTotal, String newDetails) {
        Payment payment = getPaymentById(id); // Reuses the existing getPaymentById method
        validatePaymentInputs(newPaidDate, newTotal, newDetails);

        payment.setPaidDate(newPaidDate);
        payment.setTotal(newTotal);
        payment.setDetails(newDetails);

        return paymentRepository.save(payment);
    }

    @Transactional
    public void deletePayment(int id) {
        Payment payment = getPaymentById(id);
        paymentRepository.delete(payment);
    }

    private void validatePaymentInputs(Date paidDate, double total, String details) {
        if (paidDate == null) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Payment date cannot be null.");
        }
        if (total <= 0) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Payment total must be greater than zero.");
        }
        if (details == null || details.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "Payment details cannot be empty.");
        }
    }
}

