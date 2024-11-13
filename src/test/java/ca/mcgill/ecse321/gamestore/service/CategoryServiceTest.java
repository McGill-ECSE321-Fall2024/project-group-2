
package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    // Constants used to represent valid and invalid data in test cases
    private static final String VALID_NAME = "Action";
    private static final String VALID_NAME2 = "RPG";

    // Setup mocks with lenient behavior before each test
    @BeforeEach
    public void setUpMocks() {
        // Mock save method to return the category object passed as an argument
        lenient().when(categoryRepository.save(any(Category.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

     // Test for successful category creation
     @Test
     public void testCreateCategory_Success() {
         Category category = new Category(VALID_NAME);
         when(categoryRepository.save(any(Category.class))).thenReturn(category);
 
         Category createdCategory = categoryService.createCategory(VALID_NAME);
         assertNotNull(createdCategory);
         assertEquals(VALID_NAME, createdCategory.getName());
     }

     // Test for category creation with a null name
    @Test
    public void testCreateCategory_InvalidName() {
        GameStoreException exception = assertThrows(GameStoreException.class, () -> categoryService.createCategory(null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The name cannot be empty!", exception.getMessage());
    }

    // Test retrieving a category by a valid ID
    @Test
    public void testGetCategoryById_Success() {
        Category category = new Category(VALID_NAME);
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(category);

        Category foundCategory = categoryService.getCategory(1);
        assertNotNull(foundCategory);
        assertEquals(VALID_NAME, foundCategory.getName());
    }

    // Test retrieving a category by an ID that does not exist
    @Test
    public void testGetCategoryById_NotFound() {
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                categoryService.getCategory(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find category with the given Id!", exception.getMessage());
    }

    // Test retrieving all categories
    @Test
    public void testGetAllCategories() {
        Category category1 = new Category(VALID_NAME);
        Category category2 = new Category(VALID_NAME2);
        when(categoryRepository.findAll()).thenReturn(List.of(category1, category2));

        List<Category> categories = categoryService.getAllCategory();
        assertEquals(2, categories.size());
    }

    // Test updating an existing category name
    @Test
    public void testUpdateCategory_Success() {
        Category category = new Category(VALID_NAME);
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(category);

        Category updatedCategory = categoryService.updateCategoryName(1, VALID_NAME2);
        assertNotNull(updatedCategory);
        assertEquals(VALID_NAME2, updatedCategory.getName());
    }

    // Test updating a category name with an ID that does not exist
    @Test
    public void testUpdateCategory_NotFound() {
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(null);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                categoryService.updateCategoryName(999, VALID_NAME2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find category with the given Id!", exception.getMessage());
    }

    // Test updating a category name with a null name
    @Test
    public void testUpdateCategory_InvalidName() {
        Category category = new Category(VALID_NAME);
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(category);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                categoryService.updateCategoryName(1, null));

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The name cannot be empty!", exception.getMessage());
    }


    // Test deleting an existing category
    @Test
    public void testDeleteCategory_Success() {
        Category category = new Category(VALID_NAME);
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(category);
        boolean isDeleted= categoryService.deleteCategory(1);
        verify(categoryRepository, times(1)).delete(category);
        assertTrue(isDeleted);
    }

    // Test deleting a category with an ID that does not exist
    @Test
    public void testDeleteCategory_NotFound() {
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                categoryService.deleteCategory(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find category with the given Id!", exception.getMessage());
    }



}
