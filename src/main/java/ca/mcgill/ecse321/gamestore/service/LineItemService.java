package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.model.Order;
import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import ca.mcgill.ecse321.gamestore.model.WishList;
import ca.mcgill.ecse321.gamestore.repository.LineItemRepository;
import ca.mcgill.ecse321.gamestore.repository.ShoppingCartRepository;
import ca.mcgill.ecse321.gamestore.repository.WishListRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineItemService {

    @Autowired
    private LineItemRepository lineItemRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private WishListRepository wishListRepository;

    @Transactional
    public LineItem getLineItem(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        LineItem lineItem = lineItemRepository.findLineItemById(id);
        if (lineItem == null) {
            throw new IllegalArgumentException("LineItem not found");
        }
        return lineItem;
    }

    @Transactional
    public LineItem createLineItem(int quantity, double price) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }

        LineItem lineItem = new LineItem();
        lineItem.setQuantity(quantity);
        lineItem.setPrice(price);

        return lineItemRepository.save(lineItem);
    }

    @Transactional
    public LineItem updateLineItemQuantity(int id, int quantity) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        LineItem lineItem = lineItemRepository.findLineItemById(id);
        if (lineItem == null) {
            throw new IllegalArgumentException("LineItem not found");
        }
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        lineItem.setQuantity(quantity);
        return lineItemRepository.save(lineItem);
    }

    @Transactional
    public LineItem updateLineItemPrice(int id, double price) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        LineItem lineItem = lineItemRepository.findLineItemById(id);
        if (lineItem == null) {
            throw new IllegalArgumentException("LineItem not found");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        lineItem.setPrice(price);
        return lineItemRepository.save(lineItem);
    }

    @Transactional
    public boolean deleteLineItem(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        LineItem lineItem = lineItemRepository.findLineItemById(id);
        if (lineItem == null) {
            throw new IllegalArgumentException("LineItem not found");
        }
        lineItemRepository.delete(lineItem);
        return true;
    }

    @Transactional
    public LineItem addToCart(int lineItemId, int cartId) {
        if (lineItemId < 0 || cartId < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        LineItem lineItem = lineItemRepository.findLineItemById(lineItemId);
        ShoppingCart cart = shoppingCartRepository.findById(cartId).orElse(null);

        if (lineItem == null || cart == null) {
            throw new IllegalArgumentException("LineItem or ShoppingCart not found");
        }

        lineItem.setCart(cart);
        return lineItemRepository.save(lineItem);
    }

    @Transactional
    public LineItem addToWishlist(int lineItemId, int wishlistId) {
        if (lineItemId < 0 || wishlistId < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        LineItem lineItem = lineItemRepository.findLineItemById(lineItemId);
        WishList wishlist = wishListRepository.findById(wishlistId).orElse(null);

        if (lineItem == null || wishlist == null) {
            throw new IllegalArgumentException("LineItem or WishList not found");
        }

        lineItem.setWishlist(wishlist);
        return lineItemRepository.save(lineItem);
    }

    @Transactional
    public LineItem removeFromCart(int lineItemId) {
        if (lineItemId < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        LineItem lineItem = lineItemRepository.findLineItemById(lineItemId);
        if (lineItem == null || lineItem.getCart() == null) {
            throw new IllegalArgumentException("LineItem or associated Cart not found");
        }
        Order tempOrder = lineItem.getOrder();
        WishList tempWishlist = lineItem.getWishlist();
        lineItem.delete();
        lineItem.setOrder(tempOrder);
        lineItem.setWishlist(tempWishlist);
        return lineItemRepository.save(lineItem);
    }

    @Transactional
    public LineItem removeFromWishlist(int lineItemId) {
        if (lineItemId < 0) {
            throw new IllegalArgumentException("ID cannot be negative");
        }
        LineItem lineItem = lineItemRepository.findLineItemById(lineItemId);
        if (lineItem == null || lineItem.getWishlist() == null) {
            throw new IllegalArgumentException("LineItem or associated WishList not found");
        }
        Order tempOrder = lineItem.getOrder();
        ShoppingCart tempCart = lineItem.getCart();
        lineItem.delete();
        lineItem.setCart(tempCart);
        lineItem.setOrder(tempOrder);
        return lineItemRepository.save(lineItem);
    }
}
