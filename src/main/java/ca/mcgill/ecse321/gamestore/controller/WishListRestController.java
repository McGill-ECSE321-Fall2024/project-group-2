package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.WishListRequestDto;
import ca.mcgill.ecse321.gamestore.dto.WishListResponseDto;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.WishList;
import ca.mcgill.ecse321.gamestore.service.WishListService;

@RestController
public class WishListRestController {

    @Autowired
    WishListService wishListService;

    /**
     * Create a new wishlist.
     *
     * @param wishListDto The wishlist to create.
     * @return The created wishlist.
     */
    @PostMapping("/wishlist")
    public ResponseEntity<?> createWishList(@RequestBody WishListRequestDto wishListDto){
        try {
            WishList createdWishList = wishListService.createWishList(wishListDto.getWishName());
            return new ResponseEntity<>(new WishListResponseDto(createdWishList), HttpStatus.CREATED);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }


    /**
     * Create a new wishlist.
     *
     * @param wishListId The primary key of the wishlist to update.
     * @param newName The new name
     * @return The created wishlist.
     */
    @PutMapping("/wishlist/{listId}")
    public ResponseEntity<?> updateWishListName(@PathVariable int listId, @RequestParam String newName){
        try {
            WishList wishList = wishListService.updateWishListName(listId, newName);
            return new ResponseEntity<>(new WishListResponseDto(wishList), HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    /**
     * Return the wishlist with the given ID.
     *
     * @param listId The primary key of the wishlist to find.
     * @return The wishlist with the given ID.
     */
    @GetMapping("/wishlist/{listId}")
    public ResponseEntity<?> getWishList(@PathVariable int listId){
        try {
            WishList wishList = wishListService.getWishList(listId);
            return new ResponseEntity<>(new WishListResponseDto(wishList), HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    /**
     * Delete the wishlist with the given ID.
     *
     * @param listId The primary key of the wishlist to delete.
     * @return Boolean indicating whether the wishlist has been sucessfully deleted or not.
     */
    @DeleteMapping("/wishlist/{listId}")
    public ResponseEntity<?> deleteWishList(@PathVariable int listId) {
        try {
            wishListService.deleteWishList(listId);
            return new ResponseEntity<>("WishList was deleted successfully.", HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

}
