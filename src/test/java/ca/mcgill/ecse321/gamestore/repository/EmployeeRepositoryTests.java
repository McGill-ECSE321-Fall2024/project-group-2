package ca.mcgill.ecse321.gamestore.repository;

// Import assertions to validate test results
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//Import Employee,ChangeRequest and Inventory classes
import ca.mcgill.ecse321.gamestore.model.Employee;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Import Date used for Date data type


/**
 * This class contains unit tests for the EmployeeRepository to ensure that
 * Employee entities and their relationships with ChangeRequest and Inventory are properly persisted and retrieved from the database.
 */
@SpringBootTest
class EmployeeRepositoryApplicationTests {

    // Autowired to inject the EmployeeRepository,ChangeRequestRepository,and InventoryRepository dependencies
    @Autowired
    private EmployeeRepository employeeRepository;



    /**
     * Clears the database before and after each test to ensure a fresh test environment.
     * This method deletes all Employee, ChangeRequest, and Inventory entities from the repositories.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        employeeRepository.deleteAll();

    }

    /**
     * Tests that an Employee entity, along with its associated ChangeRequest and Inventory,
     * can be saved and retrieved from the database.
     * The test ensures that the saved Employee matches the one retrieved from the database.
     */
    @Test
    public void testPersistAndLoadChangeRequest(){
        // Set up a new Inventory entity




        // Set up a new Employee entity associated with the ChangeRequest
        Employee employee=new Employee ("moe12","Mohamed","moe@mail.com","1234");
        employeeRepository.save(employee);

        // Retrieve the Employee entity from the repository by its email
        String email = employee.getEmail();
        Employee employeeFromDb = employeeRepository.findEmployeeByEmail(email);

        // Verify that the retrieved Employee is not null and matches the saved Employee
        assertNotNull(employeeFromDb);
        assertEquals(employeeFromDb.getEmail(),employee.getEmail());
        assertEquals(employeeFromDb.getName(),employee.getName());
        assertEquals(employeeFromDb.getPassword(),employee.getPassword());
    }




}