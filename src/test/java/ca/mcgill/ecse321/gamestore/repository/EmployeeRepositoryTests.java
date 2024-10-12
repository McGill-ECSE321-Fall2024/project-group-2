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
class EmployeeRepositoryApplicationTests {


    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private ChangeRequestRepository changeRequestRepository;
    @Autowired
    private InventoryRepository inventoryRepository;



    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        employeeRepository.deleteAll();

        changeRequestRepository.deleteAll();
        inventoryRepository.deleteAll();





    }

    @Test
    public void testPersistAndLoadChangeRequest(){

        Inventory inventory = new Inventory(13);
        inventoryRepository.save(inventory);
        ChangeRequest changeRequest=new ChangeRequest (Date.valueOf("2024-10-09"),RequestStatus.InProgress,inventory);
        changeRequestRepository.save(changeRequest);

        Employee employee=new Employee (null,"moe12","Mohamed","moe@mail.com","1234",changeRequest);
        employeeRepository.save(employee);
        String email = employee.getEmail();
        Employee employeeFromDb = employeeRepository.findEmployeeByEmail(email);




        // Read Category from database
        assertNotNull(employeeFromDb);
        assertEquals(employeeFromDb.getEmail(),employee.getEmail());
        assertEquals(employeeFromDb.getName(),employee.getName());
        assertEquals(employeeFromDb.getPassword(),employee.getPassword());

        assertEquals(employeeFromDb.getChangeRequest().getId(), changeRequest.getId());
        assertEquals(employeeFromDb.getChangeRequest().getInventory().getId(), employee.getChangeRequest().getInventory().getId());
    }




}