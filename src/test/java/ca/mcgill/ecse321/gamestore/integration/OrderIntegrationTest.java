/*
package ca.mcgill.ecse321.gamestore.integration;

import ca.mcgill.ecse321.gamestore.dto.OrderRequestDto;
import ca.mcgill.ecse321.gamestore.dto.OrderResponseDto;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;
import ca.mcgill.ecse321.gamestore.service.OrderService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OrderIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private OrderService orderService;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        orderService.getAllOrders().forEach(order -> orderService.deleteOrder(order.getId()));
    }

    @Test
    public void testCreateOrder() {
        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setNumber(1);
        orderRequest.setOrderedDate(Date.valueOf(LocalDate.now()));
        orderRequest.setShipTo("123 Main St");
        orderRequest.setTotal(100.0);
        orderRequest.setStatus(OrderStatus.Pending);

        ResponseEntity<OrderResponseDto> response = restTemplate.postForEntity("/orders/create", orderRequest, null);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        OrderResponseDto createdOrder = response.getBody();
        assertNotNull(createdOrder);
        assertEquals(1, createdOrder.getNumber());
        assertEquals("123 Main St", createdOrder.getShipTo());
        assertEquals(100.0, createdOrder.getTotal());
        assertEquals(OrderStatus.Pending, createdOrder.getStatus());
    }

    @Test
    public void testGetOrderById() {
        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setNumber(1);
        orderRequest.setOrderedDate(Date.valueOf(LocalDate.now()));
        orderRequest.setShipTo("123 Main St");
        orderRequest.setTotal(100.0);
        orderRequest.setStatus(OrderStatus.Pending);

        OrderResponseDto createdOrder = restTemplate.postForEntity("/orders/create", orderRequest, OrderResponseDto.class).getBody();
        assertNotNull(createdOrder);

        ResponseEntity<OrderResponseDto> response = restTemplate.getForEntity("/orders/" + createdOrder.getOrderId(), OrderResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(createdOrder.getOrderId(), response.getBody().getOrderId());
    }

    @Test
    public void testUpdateOrderStatus() {
        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setNumber(2);
        orderRequest.setOrderedDate(Date.valueOf(LocalDate.now()));
        orderRequest.setShipTo("123 Main St");
        orderRequest.setTotal(200.0);
        orderRequest.setStatus(OrderStatus.Pending);

        OrderResponseDto createdOrder = restTemplate.postForEntity("/orders/create", orderRequest, OrderResponseDto.class).getBody();
        assertNotNull(createdOrder);

        ResponseEntity<OrderResponseDto> response = restTemplate.exchange(
                "/orders/" + createdOrder.getOrderId() + "/status?newStatus=Shipped",
                HttpMethod.PUT,
                null,
                OrderResponseDto.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(OrderStatus.Shipped, response.getBody().getStatus());
    }

    @Test
    public void testUpdateShippingDetails() {
        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setNumber(3);
        orderRequest.setOrderedDate(Date.valueOf(LocalDate.now()));
        orderRequest.setShipTo("123 Main St");
        orderRequest.setTotal(150.0);
        orderRequest.setStatus(OrderStatus.Pending);

        OrderResponseDto createdOrder = restTemplate.postForEntity("/orders/create", orderRequest, OrderResponseDto.class).getBody();
        assertNotNull(createdOrder);

        orderRequest.setShippedDate(Date.valueOf(LocalDate.now().plusDays(3)));
        orderRequest.setShipTo("456 Elm St");

        HttpEntity<OrderRequestDto> requestEntity = new HttpEntity<>(orderRequest);

        ResponseEntity<OrderResponseDto> response = restTemplate.exchange(
                "/orders/" + createdOrder.getOrderId() + "/shipping",
                HttpMethod.PUT,
                requestEntity,
                OrderResponseDto.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("456 Elm St", response.getBody().getShipTo());
        assertEquals(orderRequest.getShippedDate(), response.getBody().getShippedDate());
    }

    @Test
    public void testDeleteOrder() {
        OrderRequestDto orderRequest = new OrderRequestDto();
        orderRequest.setNumber(4);
        orderRequest.setOrderedDate(Date.valueOf(LocalDate.now()));
        orderRequest.setShipTo("123 Main St");
        orderRequest.setTotal(250.0);
        orderRequest.setStatus(OrderStatus.Pending);

        OrderResponseDto createdOrder = restTemplate.postForEntity("/orders/create", orderRequest, OrderResponseDto.class).getBody();
        assertNotNull(createdOrder);

        ResponseEntity<Boolean> deleteResponse = restTemplate.exchange(
                "/orders/" + createdOrder.getOrderId(),
                HttpMethod.DELETE,
                null,
                Boolean.class
        );

        assertNotNull(deleteResponse);
        assertEquals(HttpStatus.OK, deleteResponse.getStatusCode());
        assertTrue(deleteResponse.getBody());

        ResponseEntity<String> getResponse = restTemplate.getForEntity("/orders/" + createdOrder.getOrderId(), String.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
        assertEquals("Order with ID " + createdOrder.getOrderId() + " not found.", getResponse.getBody());
    }
}
/*/
