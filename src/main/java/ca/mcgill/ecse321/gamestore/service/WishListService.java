package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.WishList;
import ca.mcgill.ecse321.gamestore.repository.WishListRepository;
import jakarta.transaction.Transactional;

@Service
public class WishListService {
    @Autowired
    WishListRepository wishListRepository;

    @Transactional
    public WishList createWishList(String wishName){
        WishList wishList = new WishList(wishName);
        return wishListRepository.save(wishList);
    }

    @Transactional
    public boolean deleteWishList(int listId){
        WishList wishList = wishListRepository.findWishListById(listId);
        try{
            if (wishList==null){
                throw new IllegalArgumentException("Can't find Wishlist to delete");
            }
            else{
                wishListRepository.delete(wishList);
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
    }
    @Transactional
    public WishList updateWishListName(int wishListId, String name){
        WishList wishList = wishListRepository.findWishListById(wishListId);
        if (wishList==null){
            throw new IllegalArgumentException( "WishList Not Found!");
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
            throw new IllegalArgumentException("Can't find Wishlist");
        }
        else{
            return wishList;
        }
    }

}
