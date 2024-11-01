package ca.mcgill.ecse321.gamestore.service;

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
    public WishList createWishList(int aNumberItem){
        WishList wishList = new WishList(aNumberItem);
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
