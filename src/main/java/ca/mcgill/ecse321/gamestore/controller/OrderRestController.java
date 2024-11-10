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

    @PostMapping("/create")
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

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable int id) {
        Order order = orderService.findOrderById(id);
        return new OrderResponseDto(order);
    }

    @PutMapping("/{id}/status")
    public OrderResponseDto updateOrderStatus(@PathVariable int id, @RequestParam OrderStatus newStatus) {
        Order updatedOrder = orderService.updateOrderStatus(id, newStatus);
        return new OrderResponseDto(updatedOrder);
    }

    @PutMapping("/{id}/shipping")
    public OrderResponseDto updateShippingDetails(@PathVariable int id, @RequestBody OrderRequestDto orderRequest) {
        Order updatedOrder = orderService.updateShippingDetails(id, orderRequest.getShippedDate(), orderRequest.getShipTo());
        return new OrderResponseDto(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public boolean deleteOrder(@PathVariable int id) {
        return orderService.deleteOrder(id);
    }
}
