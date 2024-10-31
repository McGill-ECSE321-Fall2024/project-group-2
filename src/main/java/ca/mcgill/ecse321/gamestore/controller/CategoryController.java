package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping(value= {"/category/create", "/category/create/"})
    public CategoryDto createCategory(@RequestBody CategoryDto categoryDto){
        Category createdCategory = categoryService.createCategory(categoryDto.getName(), categoryDto.getNumberItems());
        return new CategoryDto(createdCategory);
    }

    /**
     * Return the category with the given ID.
     *
     * @param categoryId The primary key of the category to find.
     * @return The category with the given ID.
     */
    @GetMapping(value={"/category/{categoryId}", "/category/{categoryId}/"})
    public CategoryDto getCategory(@PathVariable int categoryId){
        Category category= categoryService.getCategory(categoryId);
        return new CategoryDto(category);
    }

    /**
     * Delete the category with the given ID.
     *
     * @param categoryDto The primary key of the category to delete.
     * @return Boolean indicating whether the category has been sucessfully deleted or not.
     */
    @PostMapping(value= {"/category/delete", "/category/delete/"})
    public boolean deleteCategory(@RequestBody CategoryDto categoryDto){
        boolean deleted= categoryService.deleteCategory(categoryDto.getId());
        return deleted;
    }

    /**
     * Update the name of the category with the given ID.
     *
     * @param categoryDto The primary key of the category to update.
     * @param newName The new name for the category.
     * @return The updated category.
     */
    @PutMapping(value = { "/category/update/{name}", "/category/update/{name}/" })
    public CategoryDto updateCategoryName(@RequestBody CategoryDto categoryDto,  @PathVariable("name") String newName) {
        Category category = categoryService.updateCategoryName(categoryDto.getId(), newName);
        return new CategoryDto(category);
    }

}
