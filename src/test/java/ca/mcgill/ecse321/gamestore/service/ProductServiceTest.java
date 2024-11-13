package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    // Constants used to represent valid and invalid data in test cases
     private static final String VALID_NAME = "FIFA";
     private static final String VALID_DESCRIPTION = "Soccer game";
     private static final String VALID_NAME2 = "DOTA";
     private static final String VALID_DESCRIPTION2 = "RPG game";
     


     // Setup mocks with lenient behavior before each test
    @BeforeEach
    public void setUpMocks() {
        // Mock save method to return the product object passed as an argument
        lenient().when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

     // Test for successful product creation
     @Test
     public void testCreateProduct_Success() {

        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);

        // Mock repository behaviors for successful path
        when(productRepository.save(any(Product.class))).thenReturn(product);
 
        Product createdProduct = productService.createProduct(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        assertNotNull(createdProduct);
        assertEquals(VALID_NAME, createdProduct.getName());
        assertEquals(VALID_DESCRIPTION, createdProduct.getDescription());
        assertEquals(lineItem, createdProduct.getLineItemOfProduct());
        assertEquals(category, createdProduct.getCategory());
     }

     // Test for product creation with a null name
     @Test
     public void testCreateProduct_InvalidName() {

        // Create test category and lineItem
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> 
        productService.createProduct(null,VALID_DESCRIPTION,lineItem,category));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The name cannot be empty!", exception.getMessage());
     }

     // Test for product creation with a null description
     @Test
     public void testCreateProduct_InvalidDescription() {

        // Create test category and lineItem
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> 
        productService.createProduct(VALID_NAME,null,lineItem,category));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The description cannot be empty!", exception.getMessage());
     }

     // Test for product creation with a null description
     @Test
     public void testCreateProduct_InvalidLineItem() {

        // Create test category
        Category category = new Category("Sport");

        GameStoreException exception = assertThrows(GameStoreException.class, () -> 
        productService.createProduct(VALID_NAME,VALID_DESCRIPTION,null,category));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("LineItem cannot be empty!", exception.getMessage());
     }

     // Test for product creation with a null description
     @Test
     public void testCreateProduct_InvalidCategory() {

        // Create test LineItem
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);

        GameStoreException exception = assertThrows(GameStoreException.class, () -> 
        productService.createProduct(VALID_NAME,VALID_DESCRIPTION,lineItem,null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The category cannot be empty!", exception.getMessage());
     }

     // Test retrieving a product by a valid ID
    @Test
    public void testGetProductById_Success() {

        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);

        when(productRepository.findProductById(anyInt())).thenReturn(product);

        Product foundProduct = productService.getProduct(1);
        assertNotNull(foundProduct);
        assertEquals(VALID_NAME, foundProduct.getName());
        assertEquals(VALID_DESCRIPTION, foundProduct.getDescription());
        assertEquals(lineItem, foundProduct.getLineItemOfProduct());
        assertEquals(category, foundProduct.getCategory());
    }

     // Test retrieving a product by an ID that does not exist
    @Test
    public void testGetProductById_NotFound() {
        when(productRepository.findProductById(anyInt())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                productService.getProduct(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find product with the given Id!", exception.getMessage());
    }

    // Test retrieving all products
    @Test
    public void testGetAllProducts() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product1= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        Product product2= new Product(VALID_NAME2, VALID_DESCRIPTION2, lineItem, category);

        when(productRepository.findAll()).thenReturn(List.of(product1, product2));

        List<Product> products = productService.getAllProduct();
        assertEquals(2, products.size());
    }

    // Test retrieving all products with a category
    @Test
    public void testGetAllProductsWithCategory() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product1= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        Product product2= new Product(VALID_NAME2, VALID_DESCRIPTION2, lineItem, category);

        when(productRepository.findAllByCategoryId(1)).thenReturn(List.of(product1, product2));

        List<Product> products = productService.getAllProductWithCategory(1);
        assertEquals(2, products.size());
    }

    // Test updating an existing product name
    @Test
    public void testUpdateProductName_Success() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        when(productRepository.findProductById(anyInt())).thenReturn(product);

        Product updatedProduct = productService.updateProductName(1, VALID_NAME2);
        assertNotNull(updatedProduct);
        assertEquals(VALID_NAME2, updatedProduct.getName());
    }
    
    // Test updating an existing product name with an ID that does not exist
    @Test
    public void testUpdateProductName_NotFound() {
        when(productRepository.findProductById(anyInt())).thenReturn(null);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                productService.updateProductName(999, VALID_NAME2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find product with the given Id!", exception.getMessage());
    }

    // Test updating an existing product name with a null name
    @Test
    public void testUpdateProductName_InvalidName() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        when(productRepository.findProductById(anyInt())).thenReturn(product);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                productService.updateProductName(1, null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The name cannot be empty!", exception.getMessage());
    }

    // Test updating an existing product description
    @Test
    public void testUpdateProductDescription_Success() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        when(productRepository.findProductById(anyInt())).thenReturn(product);

        Product updatedProduct = productService.updateProductDescription(1, VALID_DESCRIPTION2);
        assertNotNull(updatedProduct);
        assertEquals(VALID_DESCRIPTION2, updatedProduct.getDescription());
    }

    // Test updating an existing product description with an ID that does not exist
    @Test
    public void testUpdateProductDescription_NotFound() {
        when(productRepository.findProductById(anyInt())).thenReturn(null);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                productService.updateProductDescription(999, VALID_DESCRIPTION2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find product with the given Id!", exception.getMessage());
    }

    // Test updating an existing product name with a null name
    @Test
    public void testUpdateProductDescription_InvalidDescription() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        when(productRepository.findProductById(anyInt())).thenReturn(product);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                productService.updateProductDescription(1, null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The description cannot be empty!", exception.getMessage());
    }

    // Test updating an existing product category
    @Test
    public void testUpdateProductCategory_Success() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        Category category2= new Category("Action");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        when(productRepository.findProductById(anyInt())).thenReturn(product);
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(category2);

        Product updatedProduct = productService.updateProductCategory(1, 2);
        assertNotNull(updatedProduct);
        assertEquals(category2, updatedProduct.getCategory());
    }

    // Test updating a product category with a product ID that does not exist
    @Test
    public void testUpdateProductCategory_ProductIdNotFound() {
        when(productRepository.findProductById(anyInt())).thenReturn(null);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                productService.updateProductCategory(999, 1));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find product with the given Id!", exception.getMessage());
    }

    // Test updating an existing product category with a category ID that does not exist
    @Test
    public void testUpdateProductCategory_CategoryIdNotFound() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        when(productRepository.findProductById(anyInt())).thenReturn(product);
        when(categoryRepository.findCategoryById(anyInt())).thenReturn(null);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                productService.updateProductCategory(1, 999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find category with the given Id!", exception.getMessage());
    }

    // Test deleting an existing product
    @Test
    public void testDeleteProduct_Success() {
        // Create test category and lineItem and product
        Category category = new Category("Sport");
        LineItem lineItem= new LineItem();
        lineItem.setPrice(90);
        lineItem.setQuantity(20);
        Product product= new Product(VALID_NAME, VALID_DESCRIPTION, lineItem, category);
        when(productRepository.findProductById(anyInt())).thenReturn(product);
        boolean isDeleted= productService.deleteProduct(1);
        verify(productRepository, times(1)).delete(product);
        assertTrue(isDeleted);
    }

    // Test deleting a product with an ID that does not exist
    @Test
    public void testDeleteProduct_NotFound() {
        when(productRepository.findProductById(anyInt())).thenReturn(null);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                productService.deleteProduct(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find product with the given Id!", exception.getMessage());
    }

}
