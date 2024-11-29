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

import ca.mcgill.ecse321.gamestore.dto.ProductListDto;
import ca.mcgill.ecse321.gamestore.dto.ProductRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ProductResponseDto;
import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import ca.mcgill.ecse321.gamestore.repository.LineItemRepository;
import ca.mcgill.ecse321.gamestore.repository.ProductRepository;
import ca.mcgill.ecse321.gamestore.service.CategoryService;
import ca.mcgill.ecse321.gamestore.service.LineItemService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private LineItemService lineItemService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;
    private LineItem lineItem;
    @AfterEach
    public void clearDatabase() {
        productRepository.deleteAll();
        lineItemRepository.deleteAll();
        categoryRepository.deleteAll();
    }
    // Set up test data before each test
    @BeforeEach
    public void setupTestData() {
        // Create a test category
        category = categoryService.createCategory("Action");
        // Create a test lineItem
        lineItem = lineItemService.createLineItem(1, 10.0);
    }


    // test to create a product successfullys
    @Test
    public void testCreateProduct_Success() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        ResponseEntity<ProductResponseDto> response = restTemplate.postForEntity("/product", request, ProductResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ProductResponseDto createdProduct = response.getBody();
        assertNotNull(createdProduct);
        assertEquals("CSGO", createdProduct.getName());
        assertEquals("Shooter game", createdProduct.getDescription());
    }

    // test to create a product with a null name
    @Test
    public void testCreateProduct_InvalidName() {
        ProductRequestDto request = new ProductRequestDto(1, null, "Shooter game", lineItem, category);
        ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The name cannot be empty!", response.getBody());
    }

    // test to create a product with a null description
    @Test
    public void testCreateProduct_InvalidDescription() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", null, lineItem, category);
        ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The description cannot be empty!", response.getBody());
    }

    // test to create a product with a null lineItem
    @Test
    public void testCreateProduct_InvalidLineItem() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", null, category);
        ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("LineItem cannot be empty!", response.getBody());
    }

    // test to create a product with a null category
    @Test
    public void testCreateProduct_InvalidCategory() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, null);
        ResponseEntity<String> response = restTemplate.postForEntity("/product", request, String.class);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("The category cannot be empty!", response.getBody());
    }

    // test to get a product with a valid Id
    @Test
    public void testGetProductById() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter_game", lineItem, category);
        ResponseEntity<ProductResponseDto> postResponse = restTemplate.postForEntity("/product", request, ProductResponseDto.class);
        ProductResponseDto createdProduct = postResponse.getBody();

        ResponseEntity<ProductResponseDto> responseEntity = restTemplate.getForEntity(
                "/product/" + createdProduct.getId(), ProductResponseDto.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ProductResponseDto retrievedProduct = responseEntity.getBody();
        assertNotNull(retrievedProduct);
        assertEquals("CSGO", retrievedProduct.getName());
        assertEquals("Shooter_game", retrievedProduct.getDescription());
    }

    // test to get a product with an invalid Id
    @Test
    public void testGetProductById_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("/product/99", String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find product with the given Id!", responseEntity.getBody());
    }

    // test to get all products
    @Test
    public void testGetAllProducts() {
        // Create a second test category
        Category category2 = categoryService.createCategory("RPG");
        // Create a second test lineItem
        LineItem lineItem2 = lineItemService.createLineItem(1, 90.0);
        ProductRequestDto request1 = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        restTemplate.postForEntity("/product", request1, ProductResponseDto.class);
        ProductRequestDto request2 = new ProductRequestDto(2, "GTA", "Open world", lineItem2, category2);
        restTemplate.postForEntity("/product", request2, ProductResponseDto.class);

        ResponseEntity<ProductListDto> response = restTemplate.getForEntity("/product", ProductListDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProductListDto products = response.getBody();
        assertNotNull(products);
        assertEquals(2, products.getProducts().size());
    }

    // test to get all products with category
    @Test
    public void testGetAllProductsWithCategory() {
        // Create a second test lineItem
        LineItem lineItem2 = lineItemService.createLineItem(1, 90.0);
        ProductRequestDto request1 = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        restTemplate.postForEntity("/product", request1, ProductResponseDto.class);
        ProductRequestDto request2 = new ProductRequestDto(2, "GTA", "Open world", lineItem2, category);
        restTemplate.postForEntity("/product", request2, ProductResponseDto.class);

        ResponseEntity<ProductListDto> response = restTemplate.getForEntity("/product/category/"+category.getId(), ProductListDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProductListDto products = response.getBody();
        assertNotNull(products);
        assertEquals(2, products.getProducts().size());
    }

    // test to update a valid Id product name with a valid name
    @Test
    public void testUpdateProductName_Success() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        ResponseEntity<ProductResponseDto> postResponse = restTemplate.postForEntity("/product", request, ProductResponseDto.class);
        ProductResponseDto createdProduct = postResponse.getBody();

        ResponseEntity<ProductResponseDto> responseEntity = restTemplate.exchange(
                "/product/name/" + createdProduct.getId() + "?newName=GTA",
                org.springframework.http.HttpMethod.PUT,
                null,
                ProductResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ProductResponseDto updatedProduct = responseEntity.getBody();
        assertNotNull(updatedProduct);
        assertEquals("GTA", updatedProduct.getName());
    }

    // test to update a product name with a null name
    @Test
    public void testUpdateProductName_InvalidName() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        ResponseEntity<ProductResponseDto> postResponse = restTemplate.postForEntity("/product", request, ProductResponseDto.class);
        ProductResponseDto createdProduct = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/product/name/" + createdProduct.getId() + "?newName=",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The name cannot be empty!", responseEntity.getBody());
    }

    // test to update a product name with an invalid id
    @Test
    public void testUpdateProductName_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/product/name/99?newName=GTA",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find product with the given Id!", responseEntity.getBody());
    }

    // test to update a valid Id product description with a valid description
    @Test
    public void testUpdateProductDescription_Success() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        ResponseEntity<ProductResponseDto> postResponse = restTemplate.postForEntity("/product", request, ProductResponseDto.class);
        ProductResponseDto createdProduct = postResponse.getBody();

        ResponseEntity<ProductResponseDto> responseEntity = restTemplate.exchange(
                "/product/description/" + createdProduct.getId() + "?newDescription=Open_World",
                org.springframework.http.HttpMethod.PUT,
                null,
                ProductResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ProductResponseDto updatedProduct = responseEntity.getBody();
        assertNotNull(updatedProduct);
        assertEquals("Open_World", updatedProduct.getDescription());
    }

    // test to update a product description with a null description
    @Test
    public void testUpdateProductDescription_InvalidDescription() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        ResponseEntity<ProductResponseDto> postResponse = restTemplate.postForEntity("/product", request, ProductResponseDto.class);
        ProductResponseDto createdProduct = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/product/description/" + createdProduct.getId() + "?newDescription=",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("The description cannot be empty!", responseEntity.getBody());
    }

    // test to update a product description with an invalid id
    @Test
    public void testUpdateProductDescription_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/product/description/99?newDescription=GTA",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find product with the given Id!", responseEntity.getBody());
    }

    // test to update a valid Id product category with a valid category
    @Test
    public void testUpdateProductCategory_Success() {
        //Create another category
        Category category2= categoryService.createCategory("RPG");
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        ResponseEntity<ProductResponseDto> postResponse = restTemplate.postForEntity("/product", request, ProductResponseDto.class);
        ProductResponseDto createdProduct = postResponse.getBody();

        ResponseEntity<ProductResponseDto> responseEntity = restTemplate.exchange(
                "/product/" + createdProduct.getId() + "/category/"+category2.getId(),
                org.springframework.http.HttpMethod.PUT,
                null,
                ProductResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ProductResponseDto updatedProduct = responseEntity.getBody();
        assertNotNull(updatedProduct);
        assertEquals("RPG", updatedProduct.getCategory().getName());
    }


    // test to update a product category with a null category
    @Test
    public void testUpdateProductCategory_InvalidCategory() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        ResponseEntity<ProductResponseDto> postResponse = restTemplate.postForEntity("/product", request, ProductResponseDto.class);
        ProductResponseDto createdProduct = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/product/" + createdProduct.getId() + "/category/99",
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find category with the given Id!", responseEntity.getBody());
    }

    // test to update a product category with an invalid id
    @Test
    public void testUpdateProductCategory_NotFound() {
        //Create another category
        Category category2= categoryService.createCategory("RPG");
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/product/99/category/"+category2.getId(),
                org.springframework.http.HttpMethod.PUT,
                null,
                String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find product with the given Id!", responseEntity.getBody());
    }

    // test to delete a product with a valid Id
    @Test
    public void testDeleteProduct_Success() {
        ProductRequestDto request = new ProductRequestDto(1, "CSGO", "Shooter game", lineItem, category);
        ResponseEntity<ProductResponseDto> postResponse = restTemplate.postForEntity("/product", request, ProductResponseDto.class);
        ProductResponseDto createdProduct = postResponse.getBody();

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/product/" + createdProduct.getId(), org.springframework.http.HttpMethod.DELETE, null, String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Product was deleted successfully.", responseEntity.getBody());
    }

    // test to delete a product with an invalid Id
    @Test
    public void testDeleteProduct_NotFound() {
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                "/product/99", org.springframework.http.HttpMethod.DELETE, null, String.class);

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Can't find product with the given Id!", responseEntity.getBody());
    }

}