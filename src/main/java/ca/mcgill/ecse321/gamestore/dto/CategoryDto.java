package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Category;

public class CategoryDto {
    private String name;

    //CONSTRUCTERS
    public CategoryDto() {
    }

    public CategoryDto(int categoryId, String name){
        this.name= name;
    }

    public CategoryDto(Category category){
        this.name= category.getName();
    }

    // GETTERS
    public String getName() {
        return this.name;
    }



    // SETTERS
    public boolean setName(String name){
        this.name= name;
        return true;
    }


    public boolean setId (int categoryId){
        return true;
    }

}