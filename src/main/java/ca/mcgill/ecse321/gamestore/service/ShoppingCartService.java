package ca.mcgill.ecse321.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import ca.mcgill.ecse321.gamestore.repository.ShoppingCartRepository;

import java.sql.Date;

@Service
public class ShoppingCartService {

    // Repository for interacting with the shopping cart data
    @Autowired
    ShoppingCartRepository shoppingCartRepository;

    // Retrieve all shopping carts from the database
    @Transactional
    public Iterable<ShoppingCart> findAllShoppingCarts() {
        return shoppingCartRepository.findAll();
    }

    // Create a new shopping cart with the specified creation date
    @Transactional
    public ShoppingCart createShoppingCart(Date creationDate) {
        ShoppingCart shoppingCart = new ShoppingCart(creationDate);
        return shoppingCartRepository.save(shoppingCart); // Save the new shopping cart to the database
    }

    // Retrieve a specific shopping cart by its ID
    @Transactional
    public ShoppingCart getShoppingCart(int id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(id);

        // Check if the shopping cart exists
        if (shoppingCart == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "There is no shopping cart with ID " + id + ".");
        }
        return shoppingCart; // Return the found shopping cart
    }

    // Delete a shopping cart by its ID
    @Transactional
    public boolean deleteShoppingCart(int id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findShoppingCartById(id);

        // Check if the shopping cart exists
        if (shoppingCart == null) {
            throw new GameStoreException(HttpStatus.NOT_FOUND, "The shopping cart does not exist!");
        }

        shoppingCartRepository.delete(shoppingCart); // Delete the shopping cart from the database
        return true; // Return true to indicate successful deletion
    }
}
