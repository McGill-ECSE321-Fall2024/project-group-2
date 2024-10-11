package ca.mcgill.ecse321.gamestore.repository;

import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.model.Product;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private LineItemRepository lineItemRepository;

    private Product testProduct;
    private LineItem testLineItem;

    @BeforeEach
    public void setUp() {
        // Create and save a product instance for testing
        testProduct = new Product();
        testProduct.setName("Test Product");
        testProduct.setDescription("Test Description");
        System.out.println(String.valueOf(testProduct.getId())+ testProduct.getDescription()+ testProduct.getName());
        // Create a LineItem associated with the Product
        // Create a LineItem associated with the Product
        testLineItem = new LineItem(2, 19.99); // Assuming quantity is 2 and price is 19.99
  // Associate the LineItem with the Product
        // Save the LineItem

    }

    @Test
    public void testFindProductById() {
        testProduct.setId(1);
        productRepository.save(testProduct);
        // Retrieve the product using the repository method
        // Use the saved testProduct directly
        Product foundProduct = productRepository.findProductById(testProduct.getId()); // Replace with actual ID used in your DB

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
