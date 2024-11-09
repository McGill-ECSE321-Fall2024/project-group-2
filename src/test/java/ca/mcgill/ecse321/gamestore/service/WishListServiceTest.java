package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.WishList;
import ca.mcgill.ecse321.gamestore.repository.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WishListServiceTest {
    @Mock
    private WishListRepository wishListRepository;

    @InjectMocks
    private WishListService wishListService;

    // Constants used to represent valid and invalid data in test cases
    private static final String VALID_NAME = "WishList1";
    private static final String VALID_NAME2 = "WishList2";

    // Setup mocks with lenient behavior before each test
    @BeforeEach
    public void setUpMocks() {
        // Mock save method to return the wishList object passed as an argument
        lenient().when(wishListRepository.save(any(WishList.class))).thenAnswer(invocation -> invocation.getArgument(0));
    }

     // Test for successful wishlist creation
     @Test
     public void testCreateWishList_Success() {
         WishList wishList = new WishList(VALID_NAME);
         when(wishListRepository.save(any(WishList.class))).thenReturn(wishList);
 
         WishList createdWishList = wishListService.createWishList(VALID_NAME);
         assertNotNull(createdWishList);
         assertEquals(VALID_NAME, createdWishList.getWishName());
     }

     // Test for wishList creation with a null name
    @Test
    public void testCreateWishList_InvalidName() {
        GameStoreException exception = assertThrows(GameStoreException.class, () -> wishListService.createWishList(null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The name cannot be empty!", exception.getMessage());
    }

    // Test retrieving a wishList by a valid ID
    @Test
    public void testGetWishListById_Success() {
        WishList wishList = new WishList(VALID_NAME);
        when(wishListRepository.findWishListById(anyInt())).thenReturn(wishList);

        WishList foundWishList = wishListService.getWishList(1);
        assertNotNull(foundWishList);
        assertEquals(VALID_NAME, foundWishList.getWishName());
    }

    // Test retrieving a wishList by an ID that does not exist
    @Test
    public void testGetWishListById_NotFound() {
        when(wishListRepository.findWishListById(anyInt())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                wishListService.getWishList(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find wishlist with the given Id!", exception.getMessage());
    }

    // Test updating an existing wishList name
    @Test
    public void testUpdateWishList_Success() {
        WishList wishList = new WishList(VALID_NAME);
        when(wishListRepository.findWishListById(anyInt())).thenReturn(wishList);
        WishList updatedWishList = wishListService.updateWishListName(1, VALID_NAME2);
        assertNotNull(updatedWishList);
        assertEquals(VALID_NAME2, updatedWishList.getWishName());
    }

    // Test updating an existing wishList with a null name
    @Test
    public void testUpdateWishList_InvalidName() {
        WishList wishList = new WishList(VALID_NAME);
        when(wishListRepository.findWishListById(anyInt())).thenReturn(wishList);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                wishListService.updateWishListName(1, null));
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
        assertEquals("The name cannot be empty!", exception.getMessage());
    }

    // Test updating a wishList with an ID that does not exist
    @Test
    public void testUpdateWishList_NotFound() {
        when(wishListRepository.findWishListById(anyInt())).thenReturn(null);
        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                wishListService.updateWishListName(999, VALID_NAME2));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find wishlist with the given Id!", exception.getMessage());
    }

    // Test deleting an existing wishList
    @Test
    public void testDeleteWishList_Success() {
        WishList wishList = new WishList(VALID_NAME);
        when(wishListRepository.findWishListById(anyInt())).thenReturn(wishList);
        boolean isDeleted= wishListService.deleteWishList(1);
        verify(wishListRepository, times(1)).delete(wishList);
        assertTrue(isDeleted);
    }

    // Test deleting a wishList with an ID that does not exist
    @Test
    public void testDeleteWishList_NotFound() {
        when(wishListRepository.findWishListById(anyInt())).thenReturn(null);

        GameStoreException exception = assertThrows(GameStoreException.class, () ->
                wishListService.deleteWishList(999));
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals("Can't find wishlist with the given Id!", exception.getMessage());
    }



}
