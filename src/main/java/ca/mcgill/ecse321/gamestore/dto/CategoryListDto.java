package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class CategoryListDto {
    private List<CategoryResponseDto> category;

    public CategoryListDto(List<CategoryResponseDto> category) {
        this.category = category;
    }

    public List<CategoryResponseDto> getCategory() {
        return this.category;
    }

    public void setCategory(List<CategoryResponseDto> category){
        this.category= category;
    }
}
