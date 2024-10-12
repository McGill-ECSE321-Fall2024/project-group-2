package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.Inventory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest;
import java.sql.Date;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;
@SpringBootTest
class ChangeRequestRepositoryApplicationTests {


    @Autowired
    private ChangeRequestRepository changeRequestRepository;
    @Autowired
    private InventoryRepository inventoryRepository;


    @AfterEach
    @BeforeEach
    public void clearDatabase() {
       changeRequestRepository.deleteAll();
       inventoryRepository.deleteAll();

    }

    @Test
    public void testPersistAndLoadChangeRequest(){
        Inventory inventory = new Inventory(12);
        inventoryRepository.save(inventory);

        Date date = Date.valueOf("2024-02-09");
        RequestStatus status= RequestStatus.InProgress;
        ChangeRequest changeRequest=new ChangeRequest(date,status,inventory);

        changeRequest = changeRequestRepository.save(changeRequest);
        int id = changeRequest.getId();
        ChangeRequest changeRequestFromDb = changeRequestRepository.findChangeRequestById(id);




        // Read Category from database
        assertNotNull(changeRequestFromDb);
        assertEquals(changeRequestFromDb.getTimeRequest(), date);
        assertEquals(changeRequestFromDb.getStatus(), status);
        assertEquals(changeRequestFromDb.getInventory().getId(),inventory.getId());
    }




}
