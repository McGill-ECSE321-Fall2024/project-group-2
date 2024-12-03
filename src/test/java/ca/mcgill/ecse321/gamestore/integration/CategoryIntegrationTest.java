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
import ca.mcgill.ecse321.gamestore.dto.CategoryListDto;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CategoryIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        categoryRepository.deleteAll();
    }

    // test to create a category successfully
    @Test
    public void testCreateCategory_Success() {
        CategoryDto request = new CategoryDto(1, "Action");
        ResponseEntity<CategoryDto> response = restTemplate.postForEntity("/category", request, CategoryDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        CategoryDto createdCategory = response.getBody();
        assertNotNull(createdCategory);
        assertEquals("Action", createdCategory.getName());
    }

    // test to create a category with a null name
    @Test
    public void testCreateCategory_InvalidName() {
        CategoryDto request = new CategoryDto(1, null);
        ResponseEntity<String> response = restTemplate.postForEntity("/category", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The name cannot be empty!", response.getBody());
    }

    // test to get a category with a valid Id
    @Test
    public void testGetCategoryById() {
        CategoryDto request = new CategoryDto(1, "Action");
        ResponseEntity<CategoryDto> postResponse = restTemplate.postForEntity("/category", request, CategoryDto.class);
        CategoryDto createdCategory = postResponse.getBody();

        ResponseEntity<CategoryDto> responseEntity = restTemplate.getForEntity(
                "/category/" + createdCategory.getId(), CategoryDto.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        CategoryDto retrievedCategory = responseEntity.getBody();
        assertNotNull(retrievedCategory);
        assertEquals("Action", retrievedCategory.getName());
    }

    // test to get a category with an invalid Id
    @Test
    public void testGetCategoryById_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/category/99", String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find category with the given Id!", responseEntity.getBody());
    }

    // test to get all categories
    @Test
    public void testGetAllCategories() {
        CategoryDto request1 = new CategoryDto(1, "Action");
        CategoryDto request2 = new CategoryDto(2, "RPG");
        restTemplate.postForEntity("/category", request1, CategoryDto.class);
        restTemplate.postForEntity("/category", request2, CategoryDto.class);

        ResponseEntity<CategoryListDto> response = restTemplate.getForEntity("/category", CategoryListDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        CategoryListDto categories = response.getBody();
        assertNotNull(categories);
        assertEquals(2, categories.getCategory().size());
    }

    // test to update a valid Id category name with a valid name
    @Test
    public void testUpdateCategoryName_Success() {
        CategoryDto request = new CategoryDto(1, "Action");
        ResponseEntity<CategoryDto> postResponse = restTemplate.postForEntity("/category", request, CategoryDto.class);
        CategoryDto createdCategory = postResponse.getBody();

        ResponseEntity<CategoryDto> responseEntity = restTemplate.exchange(
                "/category/" + createdCategory.getId() + "?newName=RPG",
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

    // test to update a category name with a null name
    @Test
    public void testUpdateCategoryName_InvalidName() {
        CategoryDto request = new CategoryDto(1, "Action");
        ResponseEntity<CategoryDto> postResponse = restTemplate.postForEntity("/category", request, CategoryDto.class);
        CategoryDto createdCategory = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/category/" + createdCategory.getId() + "?newName=",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The name cannot be empty!", responseEntity.getBody());
    }

    // test to update a category name with an invalid Id
    @Test
    public void testUpdateCategoryName_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/category/99?newName=RPG",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find category with the given Id!", responseEntity.getBody());
    }

    // test to delete a category with a valid Id
    @Test
    public void testDeleteCategory_Success() {
        CategoryDto request = new CategoryDto(1, "Action");
        ResponseEntity<CategoryDto> postResponse = restTemplate.postForEntity("/category", request, CategoryDto.class);
        CategoryDto createdCategory = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/category/" + createdCategory.getId(), org.springframework.http.HttpMethod.DELETE, null, String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Category was deleted successfully.", responseEntity.getBody());
    }

    // test to delete a category with an invalid Id
    @Test
    public void testDeleteCategory_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/category/99", org.springframework.http.HttpMethod.DELETE, null, String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find category with the given Id!", responseEntity.getBody());
    }
}