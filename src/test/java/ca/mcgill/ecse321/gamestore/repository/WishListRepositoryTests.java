package ca.mcgill.ecse321.gamestore.repository;
// Import necessary assertions for test validation
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
// Import gamestore model
import ca.mcgill.ecse321.gamestore.model.*;

// Import test framework annotations
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * This class contains unit tests for the WishListRepository to ensure that
 * WishList entities are correctly persisted and retrieved from the database.
 */
@SpringBootTest
class WishListRepositoryApplicationTests {
    // Autowired dependency for interacting with the WishList repository
    @Autowired
    WishListRepository wishListRepository;

    /**
     * Clears the database before and after each test to ensure a clean environment.
     * This method deletes all WishList entities from the repository.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        wishListRepository.deleteAll();
    }


    /**
     * Tests that a WishList entity can be saved and retrieved from the database.
     * It validates the fields of the retrieved WishList against the saved WishList.
     */
    @Test
    public void testPersistAndLoadShoppingCart(){
        // Create and save a WishList entity with a number of items
        WishList wishList=new WishList("Birthday");
        wishListRepository.save(wishList);

        // Retrieve the WishList entity from the repository by its ID
        int id = wishList.getId();
        WishList wishListFromDb = wishListRepository.findWishListById(id);

        // Validate that the retrieved WishList matches the saved WishList
        assertNotNull(wishListFromDb);
        assertEquals(wishListFromDb.getId(),wishList.getId());
        assertEquals(wishListFromDb.getWishName(),wishList.getWishName());

    }




}