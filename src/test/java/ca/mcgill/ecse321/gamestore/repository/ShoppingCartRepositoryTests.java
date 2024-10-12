package ca.mcgill.ecse321.gamestore.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse321.gamestore.model.*;
import ca.mcgill.ecse321.gamestore.model.Review.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321.gamestore.model.Order.OrderStatus;

import java.sql.Date;

@SpringBootTest
class ShoppingCartRepositoryApplicationTests {



    @Autowired
    ShoppingCartRepository shoppingCartRepository;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {

       shoppingCartRepository.deleteAll();



    }

    @Test
    public void testPersistAndLoadShoppingCart(){
        ShoppingCart shoppingCart=new ShoppingCart(Date.valueOf("2024-03-21"));
        shoppingCartRepository.save(shoppingCart);
        int id = shoppingCart.getId();
        ShoppingCart shoppingCartFromDb = shoppingCartRepository.findShoppingCartById(id);




        // Read Category from database
        assertNotNull(shoppingCartFromDb);
        assertEquals(shoppingCartFromDb.getId(),shoppingCart.getId());
        assertEquals(shoppingCartFromDb.getCreationDate(),shoppingCart.getCreationDate());

    }




}