package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamestore.dto.OrderRequestDto;
import ca.mcgill.ecse321.gamestore.dto.OrderResponseDto;
import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;
import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    /**
     * Create a new Order.
     *
     * @param orderRequest The OrderRequestDto containing the order details.
     * @return OrderResponseDto with the created Order's details.
     */
    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequest) {
        Payment payment = new Payment(orderRequest.getOrderedDate(), orderRequest.getTotal(), "");
        Order order = orderService.createOrder(
                orderRequest.getNumber(),
                orderRequest.getOrderedDate(),
                orderRequest.getShippedDate(),
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
        Order order = orderService.findOrderById(id);
        return new OrderResponseDto(order);
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
        Order updatedOrder = orderService.updateShippingDetails(id, orderRequest.getShippedDate(), orderRequest.getShipTo());
        return new OrderResponseDto(updatedOrder);
    }

    /**
     * Delete an Order by its ID.
     *
     * @param id The ID of the Order to delete.
     * @return A boolean indicating the success of the deletion.
     */
    @DeleteMapping("/{id}")
    public boolean deleteOrder(@PathVariable int id) {
        return orderService.deleteOrder(id);
    }
}
