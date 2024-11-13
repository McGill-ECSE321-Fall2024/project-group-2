package ca.mcgill.ecse321.gamestore.integration;

import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.service.LineItemService;
import ca.mcgill.ecse321.gamestore.repository.LineItemRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class LineItemIntegrationTest {

    @Autowired
    private LineItemService lineItemService;

    @Autowired
    private LineItemRepository lineItemRepository;

    private LineItem lineItem;

    /**
     * Sets up a sample LineItem before each test.
     */
    @BeforeEach
    public void setUp() {
        lineItem = lineItemService.createLineItem(5, 10.00);
    }

    /**
     * Cleans up the database after each test to ensure isolation between tests.
     */
    @AfterEach
    public void tearDown() {
        lineItemRepository.deleteAll();
    }

    /**
     * Tests the creation of a LineItem with valid inputs.
     * Ensures that a LineItem with the specified quantity and price is created successfully.
     */
    @Test
    public void testCreateLineItem() {
        LineItem newLineItem = lineItemService.createLineItem(3, 20.00);
        assertNotNull(newLineItem);
        assertEquals(3, newLineItem.getQuantity());
        assertEquals(20.00, newLineItem.getPrice());
    }

    /**
     * Edge Case: Tests creating a LineItem with zero quantity.
     * Expects an IllegalArgumentException due to invalid quantity.
     */
    @Test
    public void testCreateLineItem_ZeroQuantity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> lineItemService.createLineItem(0, 20.00));
        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    /**
     * Edge Case: Tests creating a LineItem with negative price.
     * Expects an IllegalArgumentException due to invalid price.
     */
    @Test
    public void testCreateLineItem_NegativePrice() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> lineItemService.createLineItem(5, -10.00));
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    /**
     * Error Case: Tests fetching a non-existent LineItem.
     * Expects an IllegalArgumentException due to invalid LineItem ID.
     */
    @Test
    public void testGetLineItem_NonExistentId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> lineItemService.getLineItem(999));
        assertEquals("LineItem not found", exception.getMessage());
    }

    /**
     * Edge Case: Tests updating LineItem quantity to zero.
     * Expects an IllegalArgumentException due to invalid quantity.
     */
    @Test
    public void testUpdateLineItemQuantity_ZeroQuantity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> lineItemService.updateLineItemQuantity(lineItem.getId(), 0));
        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    /**
     * Error Case: Tests deleting a non-existent LineItem.
     * Expects an IllegalArgumentException due to invalid LineItem ID.
     */
    @Test
    public void testDeleteLineItem_NonExistentId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> lineItemService.deleteLineItem(999));
        assertEquals("LineItem not found", exception.getMessage());
    }
}
