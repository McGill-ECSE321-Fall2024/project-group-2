package ca.mcgill.ecse321.gamestore.repository;

// Import assertions to validate test outcomes
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Import model classes and test framework annotations
import ca.mcgill.ecse321.gamestore.model.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;

//import Date data type
import java.sql.Date;

@SpringBootTest
class ChangeRequestRepositoryApplicationTests {

    // Autowired to inject dependencies for repositories
    @Autowired
    private ChangeRequestRepository changeRequestRepository;
    @Autowired
    private InventoryRepository inventoryRepository;

    /**
     * Clears the database before and after each test to ensure a clean state.
     * Deletes all data from the Inventory and ChangeRequest Repositories.
     */
    @AfterEach
    @BeforeEach
    public void clearDatabase() {
       changeRequestRepository.deleteAll();
       inventoryRepository.deleteAll();
    }

    /**
     * Tests that an ChangeRequest entity can be persisted and retrieved from the database.
     * The test creates a inventory associates it with an ChangeRequest,
     * and verifies that the saved ChangeRequest matches the retrieved ChangeRequest.
     */
    @Test
    public void testPersistAndLoadChangeRequest(){
        // Create and save an Inventory entity
        Inventory inventory = new Inventory(12);
        inventoryRepository.save(inventory);

        // Create a ChangeRequest entity with a date, status, and associated inventory
        Date date = Date.valueOf("2024-02-09");
        RequestStatus status= RequestStatus.InProgress;
        ChangeRequest changeRequest=new ChangeRequest(date,status,inventory);

        // Save the ChangeRequest entity to the repository
        changeRequest = changeRequestRepository.save(changeRequest);

        // Verify that the retrieved ChangeRequest matches the saved ChangeRequest
        int id = changeRequest.getId();
        ChangeRequest changeRequestFromDb = changeRequestRepository.findChangeRequestById(id);

        // Verify that the retrieved ChangeRequest is not null and matches the saved ChangeRequest
        assertNotNull(changeRequestFromDb);
        assertEquals(changeRequestFromDb.getId(),changeRequest.getId());
        assertEquals(changeRequestFromDb.getTimeRequest(), date);
        assertEquals(changeRequestFromDb.getStatus(), status);
        assertEquals(changeRequestFromDb.getInventory().getId(),inventory.getId());
    }




}
