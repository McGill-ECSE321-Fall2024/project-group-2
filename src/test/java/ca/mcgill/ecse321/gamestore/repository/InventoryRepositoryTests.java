package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.model.Employee;
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
class InventoryRepositoryApplicationTests {


    @Autowired
    private InventoryRepository inventoryRepository;




    @BeforeEach
    @AfterEach
    public void clearDatabase() {

        inventoryRepository.deleteAll();





    }

    @Test
    public void testPersistAndLoadInventory(){
        Inventory inventory = new Inventory(13);
        inventoryRepository.save(inventory);
        int id = inventory.getId();
        Inventory inventoryFromDb = inventoryRepository.findInventoryById(id);




        // Read Category from database
        assertNotNull(inventoryFromDb);
        assertEquals(inventoryFromDb.getId(), inventory.getId());
        assertEquals(inventoryFromDb.getNumberOfItems(),inventory.getNumberOfItems());
    }




}