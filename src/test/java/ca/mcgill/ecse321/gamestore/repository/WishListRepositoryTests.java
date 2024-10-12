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
class WishListRepositoryApplicationTests {



    @Autowired
    WishListRepository wishListRepository;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {

        wishListRepository.deleteAll();



    }

    @Test
    public void testPersistAndLoadShoppingCart(){
        WishList wishList=new WishList(14);
        wishListRepository.save(wishList);
        int id = wishList.getId();
        WishList wishListFromDb = wishListRepository.findWishListById(id);





        // Read Category from database
        assertNotNull(wishListFromDb);
        assertEquals(wishListFromDb.getId(),wishList.getId());
        assertEquals(wishListFromDb.getNumberItem(),wishList.getNumberItem());

    }




}