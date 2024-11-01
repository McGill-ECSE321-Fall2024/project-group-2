package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.WishList;

public class WishListRequestDto {
    private int numberItem;

    //CONSTRUCTERS
    public WishListRequestDto(){
    }

    public WishListRequestDto(int listId, int numberItem){
        this.numberItem= numberItem;
    }

    public WishListRequestDto(WishList wishList){
        this.numberItem= wishList.getNumberItem();
    }

    //GETTERS
    public int getNumberItem(){
        return this.numberItem;
    }


    //SETTERS
    public boolean setNumberItem(int numberItem){
        this.numberItem= numberItem;
        return true;
    }


}
