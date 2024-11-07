package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import ca.mcgill.ecse321.gamestore.model.WishList;
import ca.mcgill.ecse321.gamestore.repository.LineItemRepository;
import ca.mcgill.ecse321.gamestore.repository.ShoppingCartRepository;
import ca.mcgill.ecse321.gamestore.repository.WishListRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LineItemServiceTest {

    @Mock
    private LineItemRepository lineItemRepository;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private LineItemService lineItemService;

    private static final int VALID_LINE_ITEM_ID = 1;
    private static final int VALID_QUANTITY = 5;
    private static final double VALID_PRICE = 100.0;

    private LineItem validLineItem;

    @BeforeEach
    public void setUp() {
        validLineItem = new LineItem();
        validLineItem.setId(VALID_LINE_ITEM_ID);
        validLineItem.setQuantity(VALID_QUANTITY);
        validLineItem.setPrice(VALID_PRICE);

        // Use lenient to avoid unnecessary stubbing errors
        lenient().when(lineItemRepository.findLineItemById(anyInt())).thenAnswer(invocation -> {
            int id = invocation.getArgument(0);
            return (id == VALID_LINE_ITEM_ID) ? validLineItem : null;
        });

        lenient().when(lineItemRepository.save(any(LineItem.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

    @Test
    public void testCreateLineItem_Success() {
        when(lineItemRepository.save(any(LineItem.class))).thenReturn(validLineItem);

        LineItem createdLineItem = lineItemService.createLineItem(VALID_QUANTITY, VALID_PRICE);

        assertNotNull(createdLineItem);
        assertEquals(VALID_QUANTITY, createdLineItem.getQuantity());
        assertEquals(VALID_PRICE, createdLineItem.getPrice());
        verify(lineItemRepository, times(1)).save(any(LineItem.class));
    }

    @Test
    public void testCreateLineItem_InvalidQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                lineItemService.createLineItem(0, VALID_PRICE));
        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    @Test
    public void testCreateLineItem_InvalidPrice() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                lineItemService.createLineItem(VALID_QUANTITY, -10.0));
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    public void testGetLineItem_Success() {
        LineItem foundLineItem = lineItemService.getLineItem(VALID_LINE_ITEM_ID);

        assertNotNull(foundLineItem);
        assertEquals(VALID_LINE_ITEM_ID, foundLineItem.getId());
    }

    @Test
    public void testGetLineItem_NotFound() {
        int nonExistentId = 999;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                lineItemService.getLineItem(nonExistentId));
        assertEquals("LineItem not found", exception.getMessage());
    }

    @Test
    public void testUpdateLineItemQuantity_Success() {
        int newQuantity = 10;
        when(lineItemRepository.save(any(LineItem.class))).thenReturn(validLineItem);

        LineItem updatedLineItem = lineItemService.updateLineItemQuantity(VALID_LINE_ITEM_ID, newQuantity);

        assertNotNull(updatedLineItem);
        assertEquals(newQuantity, updatedLineItem.getQuantity());
        verify(lineItemRepository, times(1)).save(updatedLineItem);
    }

    @Test
    public void testUpdateLineItemQuantity_InvalidQuantity() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                lineItemService.updateLineItemQuantity(VALID_LINE_ITEM_ID, 0));
        assertEquals("Quantity must be greater than 0", exception.getMessage());
    }

    @Test
    public void testUpdateLineItemPrice_Success() {
        double newPrice = 120.0;
        when(lineItemRepository.save(any(LineItem.class))).thenReturn(validLineItem);

        LineItem updatedLineItem = lineItemService.updateLineItemPrice(VALID_LINE_ITEM_ID, newPrice);

        assertNotNull(updatedLineItem);
        assertEquals(newPrice, updatedLineItem.getPrice());
        verify(lineItemRepository, times(1)).save(updatedLineItem);
    }

    @Test
    public void testUpdateLineItemPrice_InvalidPrice() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                lineItemService.updateLineItemPrice(VALID_LINE_ITEM_ID, -10.0));
        assertEquals("Price cannot be negative", exception.getMessage());
    }

    @Test
    public void testDeleteLineItem_Success() {
        when(lineItemRepository.findLineItemById(VALID_LINE_ITEM_ID)).thenReturn(validLineItem);

        boolean isDeleted = lineItemService.deleteLineItem(VALID_LINE_ITEM_ID);

        assertTrue(isDeleted);
        verify(lineItemRepository, times(1)).delete(validLineItem);
    }

    @Test
    public void testAddToCart_Success() {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(2);
        when(shoppingCartRepository.findById(anyInt())).thenReturn(Optional.of(cart));
        when(lineItemRepository.save(any(LineItem.class))).thenReturn(validLineItem);

        LineItem lineItemInCart = lineItemService.addToCart(VALID_LINE_ITEM_ID, cart.getId());

        assertNotNull(lineItemInCart);
        assertEquals(cart, lineItemInCart.getCart());
        verify(lineItemRepository, times(1)).save(validLineItem);
    }

    @Test
    public void testAddToWishlist_Success() {
        WishList wishlist = new WishList();
        wishlist.setId(3);
        when(wishListRepository.findById(anyInt())).thenReturn(Optional.of(wishlist));
        when(lineItemRepository.save(any(LineItem.class))).thenReturn(validLineItem);

        LineItem lineItemInWishlist = lineItemService.addToWishlist(VALID_LINE_ITEM_ID, wishlist.getId());

        assertNotNull(lineItemInWishlist);
        assertEquals(wishlist, lineItemInWishlist.getWishlist());
        verify(lineItemRepository, times(1)).save(validLineItem);
    }

    @Test
    public void testRemoveFromCart_Success() {
        ShoppingCart cart = new ShoppingCart();
        validLineItem.setCart(cart);
        when(lineItemRepository.findLineItemById(VALID_LINE_ITEM_ID)).thenReturn(validLineItem);

        // Ensure that `lineItemRepository.save` returns the updated LineItem with cart set to null
        when(lineItemRepository.save(any(LineItem.class))).thenAnswer(invocation -> {
            LineItem updatedLineItem = invocation.getArgument(0);
            updatedLineItem.setCart(null);  // Simulate setting cart to null
            return updatedLineItem;
        });

        LineItem lineItemWithoutCart = lineItemService.removeFromCart(VALID_LINE_ITEM_ID);

        assertNotNull(lineItemWithoutCart);
        assertNull(lineItemWithoutCart.getCart());  // Ensure the cart is removed
        verify(lineItemRepository, times(1)).save(validLineItem);
    }

    @Test
    public void testRemoveFromWishlist_Success() {
        WishList wishlist = new WishList();
        validLineItem.setWishlist(wishlist);
        when(lineItemRepository.findLineItemById(VALID_LINE_ITEM_ID)).thenReturn(validLineItem);

        // Ensure that `lineItemRepository.save` returns the updated LineItem with wishlist set to null
        when(lineItemRepository.save(any(LineItem.class))).thenAnswer(invocation -> {
            LineItem updatedLineItem = invocation.getArgument(0);
            updatedLineItem.setWishlist(null);  // Simulate setting wishlist to null
            return updatedLineItem;
        });

        LineItem lineItemWithoutWishlist = lineItemService.removeFromWishlist(VALID_LINE_ITEM_ID);

        assertNotNull(lineItemWithoutWishlist);
        assertNull(lineItemWithoutWishlist.getWishlist());  // Ensure the wishlist is removed
        verify(lineItemRepository, times(1)).save(validLineItem);
    }
}
