package ca.mcgill.ecse321.gamestore.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.LineItem;






@SpringBootTest
class LineItemRepositoryTest {
	
	@Autowired
	private LineItemRepository lineItemRepository;

	@AfterEach
	public void clearDatabase() {
		lineItemRepository.deleteAll();
	}

	
	@Test
	public void testPersistAndLoadLineItem() {
		// Create LineItem
		LineItem lineItem= new LineItem(12, 20);
		// Save owner
		lineItem = lineItemRepository.save(lineItem);
		int id = lineItem.getId();

		// Read owner from database
		LineItem lineItemFromDb = lineItemRepository.findLineItemById(id);

		// Assert correct response
		assertNotNull(lineItemFromDb);
		assertEquals(lineItemFromDb.getPrice(), 20);
		assertEquals(lineItemFromDb.getQuantity(), 12);
	}
	


}
