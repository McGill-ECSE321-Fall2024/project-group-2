package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamestore.dto.CategoryListDto;
import ca.mcgill.ecse321.gamestore.dto.CategoryDto;
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
    @PostMapping(value = {"/category/create", "/category/create/"})
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto) {
        Category createdCategory = categoryService.createCategory(categoryDto.getName());
        return new CategoryDto(createdCategory);
    }

    /**
     * Return the category with the given ID.
     *
     * @param categoryId The primary key of the category to find.
     * @return The category with the given ID.
     */
    @GetMapping(value = {"/category/{categoryId}", "/category/{categoryId}/"})
    public CategoryDto getCategory(@PathVariable int categoryId) {
        Category category = categoryService.getCategory(categoryId);
        return new CategoryDto(category);
    }

    /**
     * Return all the categories.
     *
     * @return All the categories.
     */
    @GetMapping(value = {"/category", "/category/"})
    public CategoryListDto getAllCategory() {
        List<CategoryDto> categories = new ArrayList<CategoryDto>();
        for (Category category : categoryService.getAllCategory()) {
            categories.add(new CategoryDto(category));
        }
        return new CategoryListDto(categories);
    }

    /**
     * Delete the category with the given ID.
     *
     * @param categoryId The primary key of the category to delete.
     * @return Boolean indicating whether the category has been sucessfully deleted or not.
     */
    @DeleteMapping(value = {"/category/{categoryId}/", "/category/{categoryId}"})
    public boolean deleteCategory(@PathVariable int categoryId) {
        boolean deleted = categoryService.deleteCategory(categoryId);
        return deleted;
    }

    /**
     * Update the name of the category with the given ID.
     *
     * @param categoryId The primary key of the category to update.
     * @param newName    The new name for the category.
     * @return The updated category.
     */
    @PutMapping(value = {"/category/{categoryId}/name/{name}", "/category/{categoryId}/name/{name}/"})
    public CategoryDto updateCategoryName(@PathVariable("categoryId") int categoryId, @PathVariable("name") String newName) {
        Category category = categoryService.updateCategoryName(categoryId, newName);
        return new CategoryDto(category);
    }
}

