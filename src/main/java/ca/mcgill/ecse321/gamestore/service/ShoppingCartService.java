package ca.mcgill.ecse321.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.repository.ShoppingCartRepository;
import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import jakarta.transaction.Transactional;
import java.sql.Date;

@Service
public class ShoppingCartService {
    @Autowired
    ShoppingCartRepository shoppingCartRepository;
    @Transactional
    public Iterable<ShoppingCart> findAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }
    @Transactional
    public ShoppingCart createShoppingCart(Date creationDate) {
        ShoppingCart shoppingCart = new ShoppingCart(creationDate);
            return shoppingCartRepository.save(shoppingCart);
        }

    @Transactional
    public ShoppingCart getShoppingCart(int id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(id);
        if (shoppingCart == null) {
            throw new IllegalArgumentException( "Can't find shopping cart");
        }
        return shoppingCart;
    }
    @Transactional
    public boolean deleteShoppingCart(int id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(id);
        if (shoppingCart == null) {
            throw new IllegalArgumentException("Can't find shopping cart to delete");
        }
        shoppingCartRepository.delete(shoppingCart);
        return true; // Return true to indicate successful deletion
    }
}