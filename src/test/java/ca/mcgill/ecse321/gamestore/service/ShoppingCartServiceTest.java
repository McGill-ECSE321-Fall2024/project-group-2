package ca.mcgill.ecse321.gamestore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import ca.mcgill.ecse321.gamestore.repository.ShoppingCartRepository;

@SpringBootTest
public class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository repo; // Mocked repository to simulate data operations
    @InjectMocks
    private ShoppingCartService service; // Service being tested, with mocks injected

    @SuppressWarnings("null")
    @Test
    public void testCreateValidShoppingCart() {
        // Arrange: set up mock behavior and test data
        Date date = Date.valueOf("2024-05-10"); // Valid creation date for the shopping cart
        ShoppingCart shoppingCart1 = new ShoppingCart(date);
        when(repo.save(any(ShoppingCart.class))).thenReturn(shoppingCart1); // Mock save method

        // Act: call service method to create a shopping cart
        ShoppingCart createdShoppingCart = service.createShoppingCart(date);

        // Assert: check that cart was created and contains expected data
        assertNotNull(createdShoppingCart);
        assertEquals(date, createdShoppingCart.getCreationDate());

        // Verify that the save method was called once
        verify(repo, times(1)).save(any(ShoppingCart.class));
    }

    @Test
    public void testReadShoppingCartByValidId() {
        // Arrange: set up a valid shopping cart ID and mock repository response
        int id = 42;
        ShoppingCart shoppingCart = new ShoppingCart(Date.valueOf("2024-03-01"));
        when(repo.findShoppingCartById(id)).thenReturn(shoppingCart);

        // Act: retrieve shopping cart by ID
        ShoppingCart shoppingCart1 = service.getShoppingCart(id);

        // Assert: check if the retrieved cart matches expected data
        assertNotNull(shoppingCart1);
        assertEquals(shoppingCart1.getCreationDate(), shoppingCart.getCreationDate());
    }

    @Test
    public void testReadShoppingCartByInvalidId() {
        // Arrange: mock repository to return null for non-existent cart ID
        int id = 42;
        when(repo.findShoppingCartById(id)).thenReturn(null); // Simulate cart not found

        // Act and Assert: check for exception when cart does not exist
        GameStoreException e = assertThrows(GameStoreException.class, () -> service.getShoppingCart(id));
        assertEquals("There is no shopping cart with ID " + id + ".", e.getMessage());
    }

    @Test
    public void testDeleteShoppingCart() {
        // Arrange: set up valid ID and mock repository behavior for deletion
        int id = 42;
        ShoppingCart shoppingCart = new ShoppingCart(Date.valueOf("2020-05-05"));
        when(repo.findShoppingCartById(id)).thenReturn(shoppingCart); // Mock find cart by ID

        // Act: attempt to delete the cart and check the outcome
        boolean deleted = service.deleteShoppingCart(id);

        // Assert: check if the cart was successfully deleted
        assertTrue(deleted);
        verify(repo, times(1)).delete(shoppingCart); // Verify delete was called once
    }

    @Test
    public void testDeleteShoppingCartInvalidId() {
        // Arrange: mock repository to return null for a non-existent cart ID
        int id = 52; // ID that does not correspond to any existing cart
        when(repo.findShoppingCartById(id)).thenReturn(null); // Simulate cart not found

        // Act and Assert: check for exception when attempting to delete non-existent cart
        GameStoreException exception = assertThrows(GameStoreException.class, () -> {
            service.deleteShoppingCart(id);
        });

        // Assert: verify that exception message is correct
        assertEquals("The shopping cart does not exist!", exception.getMessage());

        // Verify that delete was never called on the repository
        verify(repo, times(0)).delete(any(ShoppingCart.class));
    }
}
