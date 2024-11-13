package ca.mcgill.ecse321.gamestore.integration;

import ca.mcgill.ecse321.gamestore.dto.ShoppingCartRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ShoppingCartResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ShoppingCartListDto;
import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import ca.mcgill.ecse321.gamestore.service.ShoppingCartService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.sql.Date;
import java.util.List;
import java.util.TimeZone;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ShoppingCartIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ShoppingCartService shoppingCartService;

    // Clears the database before and after each test to ensure a clean state
    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        for (ShoppingCart shoppingCart : shoppingCartService.findAllShoppingCarts()) {
            shoppingCartService.deleteShoppingCart(shoppingCart.getId());
        }
    }

    // Test creating a new shopping cart
    @Test
    public void testCreateShoppingCart() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        ShoppingCartRequestDto shoppingCartRequestDto = new ShoppingCartRequestDto(Date.valueOf("2024-11-06"));

        ResponseEntity<ShoppingCartResponseDto> responseEntity = restTemplate.postForEntity(
                "/shoppingcart/", shoppingCartRequestDto, ShoppingCartResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        ShoppingCartResponseDto createdShoppingCart = responseEntity.getBody();
        assertNotNull(createdShoppingCart);
        assertNotNull(createdShoppingCart.getId());
        assertEquals(Date.valueOf("2024-11-06"), createdShoppingCart.getCreationDate());
    }

    // Test retrieving all shopping carts
    @Test
    public void testGetAllShoppingCarts() {
        shoppingCartService.createShoppingCart(Date.valueOf("2024-11-06"));

        ResponseEntity<ShoppingCartListDto> responseEntity = restTemplate.getForEntity(
                "/shoppingcart", ShoppingCartListDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ShoppingCartListDto shoppingCartListDto = responseEntity.getBody();
        assertNotNull(shoppingCartListDto);
        List <ShoppingCartResponseDto> shoppingCarts = shoppingCartListDto.getShoppingCart();
        assertTrue(shoppingCarts.size() == 1); // Ensure that two carts exist
    }

    // Test retrieving a specific shopping cart by ID
    @Test
    public void testGetShoppingCartById() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(Date.valueOf("2024-11-06"));

        ResponseEntity<ShoppingCartResponseDto> responseEntity = restTemplate.getForEntity(
                "/shoppingcart/" + createdShoppingCart.getId(), ShoppingCartResponseDto.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        ShoppingCartResponseDto shoppingCartDto = responseEntity.getBody();
        assertNotNull(shoppingCartDto);
        assertEquals(createdShoppingCart.getId(), shoppingCartDto.getId());
        assertEquals(Date.valueOf("2024-11-06"), shoppingCartDto.getCreationDate());
    }

    // Test retrieving a non-existing shopping cart by ID
    @Test
    public void testGetNonExistingShoppingCartById() {
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "/shoppingcart/999", String.class
        );

        assertNotNull(responseEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("There is no shopping cart with ID 999.", responseEntity.getBody());
    }
}
