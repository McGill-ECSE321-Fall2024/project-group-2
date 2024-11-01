package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.WishList;

public class WishListResponseDto {
    private int listId;
    private String wishName;

    //CONSTRUCTERS
    public WishListResponseDto(){
    }

    public WishListResponseDto(int listId, String wishName){
        this.listId= listId;
        this.wishName= wishName;
    }

    public WishListResponseDto(WishList wishList){
        this.wishName= wishList.getWishName();
        this.listId= wishList.getId();
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

    public boolean setId(int listId){
        this.listId= listId;
        return true;
    }
}
