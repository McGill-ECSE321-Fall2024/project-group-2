package ca.mcgill.ecse321.gamestore;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.repository.LineItemRepository;
import ca.mcgill.ecse321.gamestore.repository.OrderRepository;
import ca.mcgill.ecse321.gamestore.repository.OwnerRepository;
import ca.mcgill.ecse321.gamestore.model.Owner;





@SpringBootTest
class GamestoreApplicationTests {

	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private OwnerRepository ownerRepository;
	@Autowired
	private LineItemRepository lineItemRepository;

	@AfterEach
	public void clearDatabase() {
		orderRepository.deleteAll();
		ownerRepository.deleteAll();
		lineItemRepository.deleteAll();
	}

	@Test
	public void testPersistAndLoadOrder() {
		// Create order
		Order order= new Order();
		order.setShipTo("Toronto");
		order.setTotal(52);

		// Save order
		order = orderRepository.save(order);
		int id = order.getId();

		// Read order from database
		Order orderFromDb = orderRepository.findOrderById(id);

		// Assert correct response
		assertNotNull(orderFromDb);
		assertEquals(orderFromDb.getShipTo(), "Toronto");
		assertEquals(orderFromDb.getTotal(), 52);
	}

	@Test
	public void testPersistAndLoadOwner() {
		// Create owner
		Owner owner= new Owner();
		owner.setEmail("Ali@gmail.com");
		owner.setName("Ali");
		owner.setUserID("owner1");

		// Save owner
		owner = ownerRepository.save(owner);
		int id = owner.getId();

		// Read owner from database
		Owner ownerFromDb = ownerRepository.findOwnerById(id);

		// Assert correct response
		assertNotNull(ownerFromDb);
		assertEquals(ownerFromDb.getUserID(), "owner1");
		assertEquals(ownerFromDb.getName(), "Ali");
		assertEquals(ownerFromDb.getEmail(), "Ali@gmail.com");
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