package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;
import ca.mcgill.ecse321.gamestore.model.Payment;
import ca.mcgill.ecse321.gamestore.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private static final int VALID_ORDER_ID = 1;
    private static final int VALID_NUMBER = 100;
    private static final Date VALID_ORDER_DATE = Date.valueOf(LocalDate.now().minusDays(1));
    private static final Date VALID_SHIPPED_DATE = Date.valueOf(LocalDate.now());
    private static final String VALID_SHIP_TO = "123 Main Street";
    private static final double VALID_TOTAL = 150.0;
    private static final OrderStatus VALID_STATUS = OrderStatus.Pending;

    private Order validOrder;

    @BeforeEach
    public void setUp() {
        Payment payment = new Payment(); // Assuming a Payment object is needed for association
        validOrder = new Order(VALID_NUMBER, VALID_ORDER_DATE, VALID_SHIPPED_DATE, VALID_SHIP_TO, VALID_TOTAL, VALID_STATUS, payment);
        validOrder.setId(VALID_ORDER_ID);

        // Use lenient for setup stubbings that might not be used in every test
        lenient().when(orderRepository.findById(anyInt())).thenAnswer(invocation -> {
            int id = invocation.getArgument(0);
            return (id == VALID_ORDER_ID) ? Optional.of(validOrder) : Optional.empty();
        });

        lenient().when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testCreateOrder_Success() {
        when(orderRepository.save(any(Order.class))).thenReturn(validOrder); // Move specific stub to this test
        Order createdOrder = orderService.createOrder(VALID_NUMBER, VALID_ORDER_DATE, VALID_SHIPPED_DATE, VALID_SHIP_TO, VALID_TOTAL, VALID_STATUS, validOrder.getPaymentOfOrder());

        assertNotNull(createdOrder);
        assertEquals(VALID_NUMBER, createdOrder.getNumber());
        assertEquals(VALID_ORDER_DATE, createdOrder.getOrderedDate());
        assertEquals(VALID_SHIPPED_DATE, createdOrder.getShippedDate());
        assertEquals(VALID_SHIP_TO, createdOrder.getShipTo());
        assertEquals(VALID_TOTAL, createdOrder.getTotal());
        assertEquals(VALID_STATUS, createdOrder.getStatus());

        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    public void testCreateOrder_InvalidOrderNumber() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(0, VALID_ORDER_DATE, VALID_SHIPPED_DATE, VALID_SHIP_TO, VALID_TOTAL, VALID_STATUS, validOrder.getPaymentOfOrder()));
        assertEquals("Order number must be positive.", exception.getMessage());
    }

    @Test
    public void testCreateOrder_InvalidTotal() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(VALID_NUMBER, VALID_ORDER_DATE, VALID_SHIPPED_DATE, VALID_SHIP_TO, -10.0, VALID_STATUS, validOrder.getPaymentOfOrder()));
        assertEquals("Total cannot be negative.", exception.getMessage());
    }

    @Test
    public void testCreateOrder_NullStatus() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(VALID_NUMBER, VALID_ORDER_DATE, VALID_SHIPPED_DATE, VALID_SHIP_TO, VALID_TOTAL, null, validOrder.getPaymentOfOrder()));
        assertEquals("Order status cannot be null.", exception.getMessage());
    }

    @Test
    public void testCreateOrder_InvalidOrderedDate() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.createOrder(VALID_NUMBER, Date.valueOf(LocalDate.now().plusDays(1)), VALID_SHIPPED_DATE, VALID_SHIP_TO, VALID_TOTAL, VALID_STATUS, validOrder.getPaymentOfOrder()));
        assertEquals("Ordered date is invalid.", exception.getMessage());
    }

    @Test
    public void testFindOrderById_Success() {
        Order order = orderService.findOrderById(VALID_ORDER_ID);

        assertNotNull(order);
        assertEquals(VALID_ORDER_ID, order.getId());
        assertEquals(VALID_NUMBER, order.getNumber());
    }

    @Test
    public void testFindOrderById_NotFound() {
        int nonExistentId = 999;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.findOrderById(nonExistentId));
        assertEquals("Order with ID " + nonExistentId + " not found.", exception.getMessage());
    }

    @Test
    public void testUpdateOrderStatus_Success() {
        OrderStatus newStatus = OrderStatus.Shipped;
        Order updatedOrder = orderService.updateOrderStatus(VALID_ORDER_ID, newStatus);

        assertNotNull(updatedOrder);
        assertEquals(newStatus, updatedOrder.getStatus());
        verify(orderRepository, times(1)).save(updatedOrder);
    }

    @Test
    public void testUpdateShippingDetails_Success() {
        Date newShippedDate = Date.valueOf(LocalDate.now());
        String newShipTo = "456 Elm Street";

        Order updatedOrder = orderService.updateShippingDetails(VALID_ORDER_ID, newShippedDate, newShipTo);

        assertNotNull(updatedOrder);
        assertEquals(newShippedDate, updatedOrder.getShippedDate());
        assertEquals(newShipTo, updatedOrder.getShipTo());
        verify(orderRepository, times(1)).save(updatedOrder);
    }

    @Test
    public void testDeleteOrder_Success() {
        when(orderRepository.findById(VALID_ORDER_ID)).thenReturn(Optional.of(validOrder));

        boolean isDeleted = orderService.deleteOrder(VALID_ORDER_ID);

        assertTrue(isDeleted);
        verify(orderRepository, times(1)).delete(validOrder);
    }

    @Test
    public void testDeleteOrder_NotFound() {
        int nonExistentId = 999;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                orderService.deleteOrder(nonExistentId));
        assertEquals("Order with ID " + nonExistentId + " not found.", exception.getMessage());
    }
}
