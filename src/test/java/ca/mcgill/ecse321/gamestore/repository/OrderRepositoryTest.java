package ca.mcgill.ecse321.gamestore.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.Order;




@SpringBootTest
class OrderRepositoryTest {
	
	@Autowired
	private OrderRepository orderRepository;

	@AfterEach
	public void clearDatabase() {
		orderRepository.deleteAll();
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

}
