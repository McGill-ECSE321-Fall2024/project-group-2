package ca.mcgill.ecse321.gamestore.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321.gamestore.dto.CategoryDto;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    // Clear the database before each test
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        categoryRepository.deleteAll();
    }

    /**
     * Test for creating a category successfully.
     */
    @Test
    public void testCreateCategory_Success() {
        // Use LocalDate for date comparison

        CategoryDto request = new CategoryDto(1, "Action");

        ResponseEntity<CategoryDto> response = restTemplate.postForEntity("/category", request, CategoryDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CategoryDto createdCategory = response.getBody();
        assertNotNull(createdCategory);

        // Convert SQL Date to LocalDate for comparison
        assertEquals("Action", createdCategory.getName());
    }

    /**
     * Test for creating a category with a null name.
     */
    @Test
    public void testCreateCategory_InvalidName() {
        CategoryDto request = new CategoryDto(1, null);
        ResponseEntity<String> response = restTemplate.postForEntity("/category", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The name cannot be empty!", response.getBody());
    }

    /**
     * Test for retrieving a category by ID.
     */
    @Test
    public void testGetCategoryById() {
        CategoryDto request = new CategoryDto(1,"Action");
        ResponseEntity<CategoryDto> postResponse = restTemplate.postForEntity("/category", request, CategoryDto.class);
        CategoryDto createdCategory = postResponse.getBody();

        ResponseEntity<CategoryDto> responseEntity = restTemplate.getForEntity(
                "/category/"+createdCategory.getId(), CategoryDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        CategoryDto retrievedCategory = responseEntity.getBody();
        assertNotNull(retrievedCategory);
        assertEquals("Action", retrievedCategory.getName());
    }

    /**
     * Test for retrieving a category by invalid ID.
     */
    @Test
    public void testGetCategoryById_NotFound() {
        ResponseEntity<CategoryDto> responseEntity = restTemplate.getForEntity(
            "/category/99", CategoryDto.class
    );

    assertNotNull(responseEntity);
    assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    assertEquals("Can't find category with the given Id!", responseEntity.getBody()); 
    }

     /**
     * Test for updating a category name with a valid new name
     */
     @Test
     public void testUpdateCategoryName_Success() {
        CategoryDto request = new CategoryDto(1,"Action");
        ResponseEntity<CategoryDto> postResponse = restTemplate.postForEntity("/category", request, CategoryDto.class);
        CategoryDto createdCategory = postResponse.getBody();
 
        ResponseEntity<CategoryDto> responseEntity = restTemplate.exchange(
                 "/category/"+createdCategory.getId()+"/name=RPG",
                 org.springframework.http.HttpMethod.PUT,
                 null,
                 CategoryDto.class
         );
 
         assertNotNull(responseEntity);
         assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
 
         CategoryDto updatedCategory = responseEntity.getBody();
         assertNotNull(updatedCategory);
         assertEquals("RPG", updatedCategory.getName());
     }


      /**
     * Test for updating a category name with a null name
     */
    @Test
    public void testUpdateCategoryName_InvalidName() {
       CategoryDto request = new CategoryDto(1,"Action");
       ResponseEntity<CategoryDto> postResponse = restTemplate.postForEntity("/category", request, CategoryDto.class);
       CategoryDto createdCategory = postResponse.getBody();

       ResponseEntity<CategoryDto> responseEntity = restTemplate.exchange(
                "/category/"+createdCategory.getId()+"/name=",
                org.springframework.http.HttpMethod.PUT,
                null,
                CategoryDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The name cannot be empty!", responseEntity.getBody());
    }

    /**
     * Test for updating a category name with invalid id
     */
    @Test
    public void testUpdateCategoryName_NotFound() {
       ResponseEntity<CategoryDto> responseEntity = restTemplate.exchange(
                "/category/99/name=RPG",
                org.springframework.http.HttpMethod.PUT,
                null,
                CategoryDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find category with the given Id!", responseEntity.getBody()); 
    }

    /**
     * Test for deleting a category with valid id
     */
    @Test
    public void testDeleteCategory_Success() {
        CategoryDto request = new CategoryDto(1,"Action");
        ResponseEntity<CategoryDto> postResponse = restTemplate.postForEntity("/category", request, CategoryDto.class);
        CategoryDto createdCategory = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/category/"+createdCategory.getId(), org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category was deleted successfully.", responseEntity.getBody());
    }

    /**
     * Test for deleting a category with invalid id
     */
    @Test
    public void testDeleteCategory_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/category/99", org.springframework.http.HttpMethod.DELETE, null, String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find category with the given Id!", responseEntity.getBody());
    }
    
}