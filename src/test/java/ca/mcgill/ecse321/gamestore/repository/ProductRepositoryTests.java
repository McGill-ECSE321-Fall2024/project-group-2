package ca.mcgill.ecse321.gamestore.repository;

import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


@DataJpaTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    public void setUp() {
        // Create and save a product instance for testing
        testProduct = new Product("Test Product", "Test Description");
        productRepository.save(testProduct);
    }

    @Test
    public void testFindProductById() {
        // Retrieve the product using the repository method
        // Use the saved testProduct directly
        Product foundProduct = productRepository.findProductById(1); // Replace with actual ID used in your DB

        // Assert that the product is found and matches the expected values
        Assertions.assertNotNull(foundProduct, "The product should be found.");
        Assertions.assertEquals("Test Product", foundProduct.getName());
        Assertions.assertEquals("Test Description", foundProduct.getDescription());
    }

    @Test
    public void testFindProductByNonExistentId() {
        // Try to find a product with a non-existing ID
        Product foundProduct = productRepository.findProductById(-1); // Using a negative ID for testing

        // Assert that the product is not found (should be null)
        Assertions.assertNull(foundProduct, "No product should be found with a non-existent ID.");
    }

    @Test
    public void testFindAllProducts() {
        // Find all products in the repository
        Iterable<Product> allProducts = productRepository.findAll();

        // Assert that the product list contains the test product
        Assertions.assertTrue(allProducts.iterator().hasNext(), "There should be at least one product in the repository.");
        Assertions.assertEquals("Test Product", allProducts.iterator().next().getName());
    }
}
