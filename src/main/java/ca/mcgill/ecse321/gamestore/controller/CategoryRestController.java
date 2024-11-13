package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamestore.dto.CategoryListDto;
import ca.mcgill.ecse321.gamestore.dto.CategoryDto;
import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.service.CategoryService;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;

@RestController
public class CategoryRestController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
        try {
            Category createdCategory = categoryService.createCategory(categoryDto.getName());
            return new ResponseEntity<>(new CategoryDto(createdCategory), HttpStatus.CREATED);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable int categoryId) {
        try {
            Category category = categoryService.getCategory(categoryId);
            return new ResponseEntity<>(new CategoryDto(category), HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @GetMapping("/category")
    public ResponseEntity<CategoryListDto> getAllCategory() {
        List<CategoryDto> categories = new ArrayList<>();
        for (Category category : categoryService.getAllCategory()) {
            categories.add(new CategoryDto(category));
        }
        return new ResponseEntity<>(new CategoryListDto(categories), HttpStatus.OK);
    }

    @DeleteMapping("/category/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable int categoryId) {
        try {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>("Category was deleted successfully.", HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    @PutMapping("/category/{categoryId}")
    public ResponseEntity<?> updateCategoryName(@PathVariable int categoryId, @RequestParam String newName) {
        try {
            Category category = categoryService.updateCategoryName(categoryId, newName);
            return new ResponseEntity<>(new CategoryDto(category), HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }
}

