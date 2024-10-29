package ca.mcgill.ecse321.gamestore.repository;

// Import necessary assertions for test validation
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

//import gamestore model
import ca.mcgill.ecse321.gamestore.model.*;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//Import Date used for date data type
import java.sql.Date;

@SpringBootTest
class ShoppingCartRepositoryApplicationTests {
    // Autowired dependency for interacting with the ShoppingCart repository
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    /**
     * Clears the database before and after each test to ensure a clean environment.
     * This method deletes all ShoppingCart entities from the repository.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
       shoppingCartRepository.deleteAll();
    }

    /**
     * Tests that a ShoppingCart entity can be saved and retrieved from the database.
     * It validates the fields of the retrieved ShoppingCart against the saved ShoppingCart.
     */

    @Test
    public void testPersistAndLoadShoppingCart(){
        // Create and save a ShoppingCart entity with a creation date
        ShoppingCart shoppingCart=new ShoppingCart(Date.valueOf("2024-03-21"));
        shoppingCartRepository.save(shoppingCart);

        // Retrieve the ShoppingCart entity from the repository by its ID
        int id = shoppingCart.getId();
        ShoppingCart shoppingCartFromDb = shoppingCartRepository.findShoppingCartById(id);

        // Ensure that the retrieved ShoppingCart is not null and matched the one saved in database
        assertNotNull(shoppingCartFromDb);
        assertEquals(shoppingCartFromDb.getId(),shoppingCart.getId());
        assertEquals(shoppingCartFromDb.getCreationDate(),shoppingCart.getCreationDate());
    }
}