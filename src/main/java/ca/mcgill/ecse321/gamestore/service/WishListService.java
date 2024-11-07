package ca.mcgill.ecse321.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.WishList;
import ca.mcgill.ecse321.gamestore.repository.WishListRepository;
import jakarta.transaction.Transactional;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;

    @Transactional
    public WishList createWishList(String wishName){
        if (wishName == null || wishName.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The name cannot be empty!");
        }
        WishList wishList = new WishList(wishName);
        return wishListRepository.save(wishList);
    }

    @Transactional
    public boolean deleteWishList(int listId){
        WishList wishList = wishListRepository.findWishListById(listId);
            if (wishList==null){
                throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find wishlist with the given Id!");
            }
            else{
                wishListRepository.delete(wishList);
                return true;
            }
    }

    @Transactional
    public WishList updateWishListName(int wishListId, String name){
        WishList wishList = wishListRepository.findWishListById(wishListId);
        if (wishList==null){
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find wishlist with the given Id!");
        }
        else if (name == null || name.trim().isEmpty()){
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The name cannot be empty!");
        }
        else{
            wishList.setWishName(name);
            return wishList;
        }
    }

    @Transactional
    public WishList getWishList(int listId){
        WishList wishList = wishListRepository.findWishListById(listId);
        if (wishList==null){
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find wishlist with the given Id!");
        }
        else{
            return wishList;
        }
    }

}
