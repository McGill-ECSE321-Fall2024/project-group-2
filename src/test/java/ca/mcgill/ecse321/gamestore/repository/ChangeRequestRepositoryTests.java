package ca.mcgill.ecse321.gamestore.repository;

// Import assertions to validate test outcomes
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Import model classes and test framework annotations
import ca.mcgill.ecse321.gamestore.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;

//import Date data type
import java.sql.Date;

@SpringBootTest
class ChangeRequestRepositoryApplicationTests {

    // Autowired to inject dependencies for repositories
    @Autowired
    private ChangeRequestRepository changeRequestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    /**
     * Clears the database before and after each test to ensure a clean state.
     * Deletes all data from the Inventory and ChangeRequest Repositories.
     */
    @AfterEach
    @BeforeEach
    public void clearDatabase() {
       changeRequestRepository.deleteAll();
       ownerRepository.deleteAll();
       employeeRepository.deleteAll();

    }

    /**
     * Tests that an ChangeRequest entity can be persisted and retrieved from the database.
     * The test creates a inventory associates it with an ChangeRequest,
     * and verifies that the saved ChangeRequest matches the retrieved ChangeRequest.
     */
    @Test
    public void testPersistAndLoadChangeRequest(){


        // Create and save an Owner entity linked to  ChangeRequest
        Owner owner = new Owner ("moe12","moe","moe@mail.com","123");
        ownerRepository.save(owner);

        Employee employee=new Employee ("moe12","Mohamed","moe2@mail.com","1234");
        employeeRepository.save(employee);


        // Create a ChangeRequest entity with a date, status, and associated inventory
        Date dateRequest = Date.valueOf("2024-02-09");
        RequestStatus statusRequest= RequestStatus.InProgress;
        ChangeRequest changeRequest=new ChangeRequest(dateRequest,statusRequest,owner,employee);

        // Save the ChangeRequest entity to the repository
        changeRequest = changeRequestRepository.save(changeRequest);

        // Verify that the retrieved ChangeRequest matches the saved ChangeRequest
        int id = changeRequest.getId();
        ChangeRequest changeRequestFromDb = changeRequestRepository.findChangeRequestById(id);

        // Verify that the retrieved ChangeRequest is not null and matches the saved ChangeRequest
        assertNotNull(changeRequestFromDb);
        assertEquals(changeRequestFromDb.getId(),changeRequest.getId());
        assertEquals(changeRequestFromDb.getRequestCreator().getEmail(),changeRequest.getRequestCreator().getEmail());
        assertEquals(changeRequestFromDb.getRequestManager().getEmail(),changeRequest.getRequestManager().getEmail());
        assertEquals(changeRequestFromDb.getTimeRequest(), dateRequest);
        assertEquals(changeRequestFromDb.getStatus(), statusRequest);
    }




}
