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

    @BeforeEach
    public void setUp() {
        // Set up a LineItem for testing purposes
        lineItem = lineItemService.createLineItem(5, 10.00);
    }

    @AfterEach
    public void tearDown() {
        lineItemRepository.deleteAll();
    }

    @Test
    public void testCreateLineItem() {
        LineItem newLineItem = lineItemService.createLineItem(3, 20.00);

        assertNotNull(newLineItem, "The created LineItem should not be null.");
        assertEquals(3, newLineItem.getQuantity(), "Quantity should be 3.");
        assertEquals(20.00, newLineItem.getPrice(), "Price should be 20.00.");
    }

    @Test
    public void testReadLineItem() {
        LineItem foundLineItem = lineItemService.getLineItem(lineItem.getId());

        assertNotNull(foundLineItem, "The fetched LineItem should not be null.");
        assertEquals(lineItem.getQuantity(), foundLineItem.getQuantity(), "Quantities should match.");
        assertEquals(lineItem.getPrice(), foundLineItem.getPrice(), "Prices should match.");
    }

    @Test
    public void testUpdateLineItemQuantity() {
        LineItem updatedLineItem = lineItemService.updateLineItemQuantity(lineItem.getId(), 10);

        assertNotNull(updatedLineItem, "Updated LineItem should not be null.");
        assertEquals(10, updatedLineItem.getQuantity(), "Updated quantity should be 10.");
        assertEquals(lineItem.getPrice(), updatedLineItem.getPrice(), "Price should remain unchanged.");
    }

    @Test
    public void testUpdateLineItemPrice() {
        LineItem updatedLineItem = lineItemService.updateLineItemPrice(lineItem.getId(), 15.00);

        assertNotNull(updatedLineItem, "Updated LineItem should not be null.");
        assertEquals(lineItem.getQuantity(), updatedLineItem.getQuantity(), "Quantity should remain unchanged.");
        assertEquals(15.00, updatedLineItem.getPrice(), "Updated price should be 15.00.");
    }

    @Test
    public void testDeleteLineItem() {
        boolean isDeleted = lineItemService.deleteLineItem(lineItem.getId());

        assertTrue(isDeleted, "The LineItem should be deleted successfully.");
        LineItem deletedLineItem = lineItemRepository.findById(lineItem.getId()).orElse(null);
        assertNull(deletedLineItem, "The deleted LineItem should not be found in the repository.");
    }
}
