package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.WishList;

public class WishListRequestDto {
    private String wishName;
    private int listId;

    //CONSTRUCTERS
    public WishListRequestDto(){
    }

    public WishListRequestDto(int listId, String wishName){
        this.listId= listId;
        this.wishName= wishName;
    }

    public WishListRequestDto(WishList wishList){
        this.listId= wishList.getId();
        this.wishName= wishList.getWishName();
    }

    //GETTERS
    public String getWishName(){
        return this.wishName;
    }

    public int getId(){
        return this.listId;
    }


    //SETTERS
    public boolean setWishName(String wishName){
        this.wishName= wishName;
        return true;
    }


}
