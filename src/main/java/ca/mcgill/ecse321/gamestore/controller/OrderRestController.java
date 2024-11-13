package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate; 
import java.sql.Date;  

import ca.mcgill.ecse321.gamestore.dto.OrderRequestDto;
import ca.mcgill.ecse321.gamestore.dto.OrderResponseDto;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;
import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.service.OrderService;
import ca.mcgill.ecse321.gamestore.service.PaymentService;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;
    /**
     * Create a new Order.
     *
     * @param orderRequest The OrderRequestDto containing the order details.
     * @return OrderResponseDto with the created Order's details.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequest) {
        Date orderedDate = convertToSqlDate(orderRequest.getOrderedDate());
        Date shippedDate = convertToSqlDate(orderRequest.getShippedDate());

        Payment payment = paymentService.createPayment(
        orderedDate, 
        orderRequest.getTotal(),
        "Order #" + orderRequest.getNumber()
        );

        Order order = orderService.createOrder(
                orderRequest.getNumber(),
                orderedDate,
                shippedDate,
                orderRequest.getShipTo(),
                orderRequest.getTotal(),
                orderRequest.getStatus(),
                payment
        );
        return new OrderResponseDto(order);
    }

    /**
     * Retrieve an Order by its ID.
     *
     * @param id The ID of the Order.
     * @return OrderResponseDto with the Order details.
     */
    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable int id) {
        try {
            Order order = orderService.findOrderById(id);
            return new OrderResponseDto(order);
        } catch (IllegalArgumentException e) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    /**
     * Update the status of an existing Order.
     *
     * @param id The ID of the Order.
     * @param newStatus The new status to be set.
     * @return OrderResponseDto with the updated Order's details.
     */
    @PutMapping("/{id}/status")
    public OrderResponseDto updateOrderStatus(@PathVariable int id, @RequestParam OrderStatus newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
        return new OrderResponseDto(updatedOrder);
    }

    /**
     * Update the shipping details of an existing Order.
     *
     * @param id The ID of the Order.
     * @param orderRequest The OrderRequestDto containing new shipping details.
     * @return OrderResponseDto with the updated Order's details.
     */
    @PutMapping("/{id}/shipping")
    public OrderResponseDto updateShippingDetails(@PathVariable int id, @RequestBody OrderRequestDto orderRequest) {
        Date shippedDate = convertToSqlDate(orderRequest.getShippedDate());

        Order updatedOrder = orderService.updateShippingDetails(id, shippedDate, orderRequest.getShipTo());
        return new OrderResponseDto(updatedOrder);
        
    }

    /**
     * Delete an Order by its ID.
     *
     * @param id The ID of the Order to delete.
     * @return A boolean indicating the success of the deletion.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public boolean deleteOrder(@PathVariable int id) {
        try {
            return orderService.deleteOrder(id);
        } catch (IllegalArgumentException e) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    // Helper method to convert LocalDate to java.sql.Date
    private Date convertToSqlDate(LocalDate localDate) {
        if (localDate != null) {
            return Date.valueOf(localDate);
        } else {
            return null;
        }
    }
}
