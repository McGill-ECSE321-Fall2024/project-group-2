package ca.mcgill.ecse321.gamestore.repository;

// Import assertions to validate test results
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//Import Customer Class
import ca.mcgill.ecse321.gamestore.model.Customer;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains unit tests for the CustomerRepository to ensure that
 * Customer entities are properly persisted and retrieved from the database.
 */
@SpringBootTest
class CustomerRepositoryApplicationTests {

    // Autowired to inject the CustomerRepository dependency
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Clears the database before and after each test to ensure a fresh test environment.
     * This method deletes all Customer entities from the repository.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
    }

    /**
     * Tests that a Customer entity can be saved and retrieved from the database.
     * The test ensures that the saved Customer matches the one retrieved from the database.
     */
    @Test
    public void testPersistAndLoadCustomer(){
        // Set up a new Customer entity
        Customer customer = new Customer(null,"Moe23","Mohamed","m.mashmoushi22@gmail.com","passowrd");

        // Save the Customer entity to the repository
        customerRepository.save(customer);

        // Retrieve the Customer entity from the repository by its email
        String email = customer.getEmail();
        Customer customerFromDb = customerRepository.findCustomerByEmail(email);

        // Verify that the retrieved Customer is not null and matches the saved Customer
        assertNotNull(customerFromDb);
        assertEquals(customerFromDb.getEmail(),customer.getEmail());
        assertEquals(customerFromDb.getUserID(), customer.getUserID());
        assertEquals(customerFromDb.getName(), customer.getName());
        assertEquals(customerFromDb.getPassword(),customer.getPassword());
    }
}