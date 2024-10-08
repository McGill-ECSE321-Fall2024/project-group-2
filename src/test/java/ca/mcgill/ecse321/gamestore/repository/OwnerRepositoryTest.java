package ca.mcgill.ecse321.gamestore.repository;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.Owner;




@SpringBootTest
class OwnerRepositoryTest {
	
	@Autowired
	private OwnerRepository ownerRepository;

	@AfterEach
	public void clearDatabase() {
		ownerRepository.deleteAll();
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

}
