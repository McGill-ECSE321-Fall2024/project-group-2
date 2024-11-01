package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Category;

public class CategoryDto {
    private int categoryId;
    private String name;

    //CONSTRUCTERS
    public CategoryDto() {
    }

    public CategoryDto(int categoryId, String name, int aNumberItems){
        this.categoryId= categoryId;
        this.name= name;
    }

    public CategoryDto(Category category){
        this.name= category.getName();
        this.categoryId= category.getId();
    }

    // GETTERS
    public String getName() {
        return this.name;
    }


    public int getId(){
        return this.categoryId;
    }

    // SETTERS
    public boolean setName(String name){
        this.name= name;
        return true;
    }


    public boolean setId (int categoryId){
        this.categoryId= categoryId;
        return true;
    }

}