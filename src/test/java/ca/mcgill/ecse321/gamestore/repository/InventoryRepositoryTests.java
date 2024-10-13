package ca.mcgill.ecse321.gamestore.repository;

// Import assertions to validate test results
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Import Inventory class
import ca.mcgill.ecse321.gamestore.model.Inventory;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains unit tests for the InventoryRepository to ensure that
 * Inventory entities are properly persisted and retrieved from the database.
 */
@SpringBootTest
class InventoryRepositoryApplicationTests {

    // Autowired to inject the InventoryRepository dependency
    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Clears the database before and after each test to ensure a fresh test environment.
     * This method deletes all Inventory entities from the repository.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        inventoryRepository.deleteAll();

    }

    /**
     * Tests that an Inventory entity can be saved and retrieved from the database.
     * The test ensures that the saved Inventory matches the one retrieved from the database.
     */
    @Test
    public void testPersistAndLoadInventory(){
        // Set up a new Inventory entity with a specific number of items
        Inventory inventory = new Inventory(13);
        inventoryRepository.save(inventory);

        // Retrieve the Inventory entity from the repository by its ID
        int id = inventory.getId();
        Inventory inventoryFromDb = inventoryRepository.findInventoryById(id);

        // Verify that the retrieved Inventory matches the saved Inventory
        assertNotNull(inventoryFromDb);
        assertEquals(inventoryFromDb.getId(), inventory.getId());
        assertEquals(inventoryFromDb.getNumberOfItems(),inventory.getNumberOfItems());
    }




}