package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

import java.sql.Date;
import ca.mcgill.ecse321.gamestore.model.ChangeRequest.RequestStatus;
@SpringBootTest
class OwnerRepositoryApplicationTests {



    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private ChangeRequestRepository changeRequestRepository;
    @Autowired
    private InventoryRepository inventoryRepository;




    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        ownerRepository.deleteAll();
        changeRequestRepository.deleteAll();
        inventoryRepository.deleteAll();





    }

    @Test
    public void testPersistAndLoadOwner(){
        Inventory inventory = new Inventory(13);
        inventoryRepository.save(inventory);
        Date date = Date.valueOf("2024-02-09");
        RequestStatus status= RequestStatus.InProgress;
        ChangeRequest changeRequest=new ChangeRequest(date,status,inventory);
        changeRequestRepository.save(changeRequest);
        Owner owner = new Owner (null,"moe12","moe","moe@mail.com","123",inventory,changeRequest);
        ownerRepository.save(owner);
        String email = owner.getEmail();
        Owner ownerFromDb = ownerRepository.findOwnerByEmail(email);




        // Read Category from database
        assertNotNull(ownerFromDb);
        assertEquals(ownerFromDb.getChangeRequest().getId(),owner.getChangeRequest().getId());
        assertEquals(ownerFromDb.getInventory().getId(),inventory.getId());
        assertEquals(ownerFromDb.getUserID(),owner.getUserID());
        assertEquals(ownerFromDb.getName(),owner.getName());
        assertEquals(ownerFromDb.getEmail(),owner.getEmail());
        assertEquals(ownerFromDb.getPassword(),owner.getPassword());




    }




}