package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.model.Category;
@SpringBootTest
class CategoryRepositoryApplicationTests {


    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {

        categoryRepository.deleteAll();


    }

    @Test
    public void testPersistAndLoadCategory(){
        String name="Mohamed";
        int numberOfItems=12;
        Category category=new Category();
        category.setName(name);
        category.setNumberItems(numberOfItems);
        category = categoryRepository.save(category);
        int id = category.getId();
        Category categoryFromDb = categoryRepository.findCategoryById(id);




        // Read Category from database
        assertNotNull(categoryFromDb);
        assertEquals(categoryFromDb.getId(), category.getId());

        assertEquals(categoryFromDb.getName(), name);
        assertEquals(categoryFromDb.getNumberItems(), numberOfItems);
    }




}







