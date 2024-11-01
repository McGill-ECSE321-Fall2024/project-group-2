package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.WishList;

public class WishListResponseDto {
    private int listId;
    private int numberItem;

    //CONSTRUCTERS
    public WishListResponseDto(){
    }

    public WishListResponseDto(int listId, int numberItem){
        this.numberItem= numberItem;
        this.listId= listId;
    }

    public WishListResponseDto(WishList wishList){
        this.numberItem= wishList.getNumberItem();
        this.listId= wishList.getId();
    }
    
    //GETTERS
    public int getNumberItem(){
        return this.numberItem;
    }

    public int getId(){
        return this.listId;
    }

    //SETTERS
    public boolean setNumberItem(int numberItem){
        this.numberItem= numberItem;
        return true;
    }

    public boolean setId(int listId){
        this.listId= listId;
        return true;
    }
}
