package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class CategoryListDto {
    private List<CategoryDto> category;

    public CategoryListDto(List<CategoryDto> category) {
        this.category = category;
    }

    public List<CategoryDto> getCategory() {
        return this.category;
    }

    public void setCategory(List<CategoryDto> category){
        this.category= category;
    }
}
