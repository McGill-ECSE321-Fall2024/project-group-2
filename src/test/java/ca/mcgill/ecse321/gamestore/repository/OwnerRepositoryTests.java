package ca.mcgill.ecse321.gamestore.repository;

// Import necessary assertions for test validation
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//import gamestore model
import ca.mcgill.ecse321.gamestore.model.*;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Import Date used for Date data type
import java.sql.Date;

/**
 * This class contains unit tests for the OwnerRepository to ensure that
 * Owner entities are correctly persisted and retrieved from the database.
 */
@SpringBootTest
class OwnerRepositoryApplicationTests {

    // Autowired dependencies for interacting with repositories
    @Autowired
    private OwnerRepository ownerRepository;


    /**
     * Clears the database before and after each test to ensure a clean environment.
     * This method deletes all Owner, ChangeRequest, and Inventory entities.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll();
    }

    /**
     * Tests that an Owner entity, along with associated ChangeRequest and Inventory,
     * can be saved and retrieved from the database. It validates the fields and associations.
     */
    @Test
    public void testPersistAndLoadOwner(){
        // Create and save an Inventory entity


        // Create and save a ChangeRequest entity linked to the Inventory
        Date date = Date.valueOf("2024-02-09");
        RequestStatus status= RequestStatus.InProgress;


        // Create and save an Owner entity linked to the Inventory and ChangeRequest
        Owner owner = new Owner ("moe12","moe","moe@mail.com","123");
        ownerRepository.save(owner);

        // Retrieve the Owner entity from the repository by its email
        String email = owner.getEmail();
        Owner ownerFromDb = ownerRepository.findOwnerByEmail(email);

        // Validate that the retrieved Owner is not null matches the saved Owner and its associations
        assertNotNull(ownerFromDb);
        assertEquals(ownerFromDb.getEmail(),owner.getEmail());
        assertEquals(ownerFromDb.getUserID(),owner.getUserID());
        assertEquals(ownerFromDb.getName(),owner.getName());
        assertEquals(ownerFromDb.getPassword(),owner.getPassword());

    }

}