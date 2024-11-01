package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.CategoryListDto;
import ca.mcgill.ecse321.gamestore.dto.CategoryRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CategoryResponseDto;
import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.service.CategoryService;

@RestController
public class CategoryController {
    
    @Autowired
    CategoryService categoryService;

    /**
     * Create a new category.
     *
     * @param categoryDto The category to create.
     * @return The created category.
     */
    @PostMapping(value= {"/category/create", "/category/create/"})
    public CategoryResponseDto createCategory(@RequestBody CategoryRequestDto categoryDto){
        Category createdCategory = categoryService.createCategory(categoryDto.getName(), categoryDto.getNumberItems());
        return new CategoryResponseDto(createdCategory);
    }

    /**
     * Return the category with the given ID.
     *
     * @param categoryId The primary key of the category to find.
     * @return The category with the given ID.
     */
    @GetMapping(value={"/category/{categoryId}", "/category/{categoryId}/"})
    public CategoryResponseDto getCategory(@PathVariable int categoryId){
        Category category= categoryService.getCategory(categoryId);
        return new CategoryResponseDto(category);
    }

    /**
     * Return all the categories.
     *
     * @return All the categories.
     */
    @GetMapping(value={"/category", "/category/"})
    public CategoryListDto getAllCategory(){
        List<CategoryResponseDto> categories = new ArrayList<CategoryResponseDto>();
        for (Category category : categoryService.getAllCategory()) {
            categories.add(new CategoryResponseDto(category));
        } return new CategoryListDto(categories);}

    /**
     * Delete the category with the given ID.
     *
     * @param categoryId The primary key of the category to delete.
     * @return Boolean indicating whether the category has been sucessfully deleted or not.
     */
    @PostMapping(value= {"/category/{categoryId}/delete", "/category/{categoryId}/delete/"})
    public boolean deleteCategory(@PathVariable int categoryId){
        boolean deleted= categoryService.deleteCategory(categoryId);
        return deleted;
    }

    /**
     * Update the name of the category with the given ID.
     *
     * @param categoryId The primary key of the category to update.
     * @param newName The new name for the category.
     * @return The updated category.
     */
    @PutMapping(value = { "/category/{categoryId}/name/{name}", "/category/{categoryId}/name/{name}/" })
    public CategoryResponseDto updateCategoryName(@PathVariable("categoryId") int categoryId,  @PathVariable("name") String newName) {
        Category category = categoryService.updateCategoryName(categoryId, newName);
        return new CategoryResponseDto(category);
    }

}
