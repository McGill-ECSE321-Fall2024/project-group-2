package ca.mcgill.ecse321.gamestore.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;
import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.repository.OrderRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Transactional
    public Order createOrder(int number, Date orderedDate, Date shippedDate, String shipTo, double total, OrderStatus status, Payment payment) {
        if (number <= 0) throw new IllegalArgumentException("Order number must be positive.");
        if (total < 0) throw new IllegalArgumentException("Total cannot be negative.");
        if (status == null) throw new IllegalArgumentException("Order status cannot be null.");
        if (orderedDate == null || orderedDate.after(Date.valueOf(LocalDate.now()))) {
            throw new IllegalArgumentException("Ordered date is invalid.");
        }

        Order order = new Order(number, orderedDate, shippedDate, shipTo, total, status, payment);
        return orderRepository.save(order);
    }

    public Order findOrderById(int id) {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new IllegalArgumentException("Order with ID " + id + " not found.");
        }
        return order.get();
    }

    @Transactional
    public Order updateOrderStatus(int id, OrderStatus newStatus) {
        Order order = findOrderById(id);
        order.setStatus(newStatus);
        return orderRepository.save(order);
    }

    @Transactional
    public Order updateShippingDetails(int id, Date shippedDate, String newShipTo) {
        Order order = findOrderById(id);
        if (shippedDate != null) {
            order.setShippedDate(shippedDate);
        }
        if (newShipTo != null && !newShipTo.trim().isEmpty()) {
            order.setShipTo(newShipTo);
        }
        return orderRepository.save(order);
    }

    @Transactional
    public boolean deleteOrder(int id) {
        Order order = findOrderById(id);
        try {
            orderRepository.delete(order);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
