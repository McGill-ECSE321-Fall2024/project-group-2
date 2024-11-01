package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.WishListRequestDto;
import ca.mcgill.ecse321.gamestore.dto.WishListResponseDto;
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
    @PostMapping(value= {"/wishlist/create", "/wishlist/create/"})
    public WishListResponseDto createWishList(@RequestBody WishListRequestDto wishListDto){
        WishList createdWishList = wishListService.createWishList(wishListDto.getNumberItem());
        return new WishListResponseDto(createdWishList);
    }

    /**
     * Return the wishlist with the given ID.
     *
     * @param listId The primary key of the wishlist to find.
     * @return The wishlist with the given ID.
     */
    @GetMapping(value={"/wishlist/{listId}", "/wishlist/{listId}/"})
    public WishListResponseDto getWishList(@PathVariable int listId){
        WishList wishList= wishListService.getWishList(listId);
        return new WishListResponseDto(wishList);
    }

    /**
     * Delete the wishlist with the given ID.
     *
     * @param listId The primary key of the wishlist to delete.
     * @return Boolean indicating whether the wishlist has been sucessfully deleted or not.
     */
    @PostMapping(value= {"/wishlist/{listId}/delete", "/wishlist/{listId}/delete/"})
    public boolean deleteWishList(@PathVariable int listId){
        boolean deleted= wishListService.deleteWishList(listId);
        return deleted;
    }


}
