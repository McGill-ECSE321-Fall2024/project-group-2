package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.WishList;

public class WishListRequestDto {
    private String wishName;

    //CONSTRUCTERS
    public WishListRequestDto(){
    }

    public WishListRequestDto(int listId, String wishName){
        this.wishName= wishName;
    }

    public WishListRequestDto(WishList wishList){
        this.wishName= wishList.getWishName();
    }

    //GETTERS
    public String getWishName(){
        return this.wishName;
    }


    //SETTERS
    public boolean setWishName(String wishName){
        this.wishName= wishName;
        return true;
    }


}
