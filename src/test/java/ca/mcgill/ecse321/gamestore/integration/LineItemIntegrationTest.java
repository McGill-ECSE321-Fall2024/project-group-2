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

        assertNotNull(newLineItem);
        assertEquals(3, newLineItem.getQuantity());
        assertEquals(20.00, newLineItem.getPrice());
    }

    @Test
    public void testReadLineItem() {
        LineItem foundLineItem = lineItemService.getLineItem(lineItem.getId());

        assertNotNull(foundLineItem);
        assertEquals(lineItem.getQuantity(), foundLineItem.getQuantity());
        assertEquals(lineItem.getPrice(), foundLineItem.getPrice());
    }

    @Test
    public void testUpdateLineItemQuantity() {
        LineItem updatedLineItem = lineItemService.updateLineItemQuantity(lineItem.getId(), 10);

        assertNotNull(updatedLineItem);
        assertEquals(10, updatedLineItem.getQuantity());
        assertEquals(lineItem.getPrice(), updatedLineItem.getPrice());
    }

    @Test
    public void testUpdateLineItemPrice() {
        LineItem updatedLineItem = lineItemService.updateLineItemPrice(lineItem.getId(), 15.00);

        assertNotNull(updatedLineItem);
        assertEquals(lineItem.getQuantity(), updatedLineItem.getQuantity());
        assertEquals(15.00, updatedLineItem.getPrice());
    }

    @Test
    public void testDeleteLineItem() {
        boolean isDeleted = lineItemService.deleteLineItem(lineItem.getId());

        assertTrue(isDeleted);
        LineItem deletedLineItem = lineItemRepository.findById(lineItem.getId()).orElse(null);
        assertNull(deletedLineItem);
    }
}
