package ca.mcgill.ecse321.gamestore.repository;

// Import assertions to validate test results
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.Category;

/**
 * This class contains unit tests for the CategoryRepository to ensure
 * that Category entities can be properly persisted and loaded from the database.
 */
@SpringBootTest
class CategoryRepositoryApplicationTests {

    // Autowired to inject the CategoryRepository dependency
    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Clears the database before and after each test to ensure a fresh test environment.
     * This method deletes all Category entities from the repository.
     */
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        categoryRepository.deleteAll();
    }

    /**
     * Tests that a Category entity can be saved and retrieved from the database.
     * The test verifies that the saved Category matches the one retrieved from the database.
     */
    @Test
    public void testPersistAndLoadCategory(){
        // Set up a new Category entity with a name and number of items and save it
        String name="Mohamed";
        int numberOfItems=12;
        Category category=new Category();
        category.setName(name);
        category.setNumberItems(numberOfItems);
        category = categoryRepository.save(category);

        // Retrieve the Category entity from the repository by its ID
        int id = category.getId();
        Category categoryFromDb = categoryRepository.findCategoryById(id);

        // Validate that the retrieved Category is not null and matches the saved Category
        assertNotNull(categoryFromDb);
        assertEquals(categoryFromDb.getId(), category.getId());
        assertEquals(categoryFromDb.getName(), name);
        assertEquals(categoryFromDb.getNumberItems(), numberOfItems);
    }




}







