package ca.mcgill.ecse321.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory (String name, int numberItems){
        Category category = new Category(name, numberItems);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category getCategory (int categoryId){
        Category category= categoryRepository.findCategoryById(categoryId);
        if (category==null){
            throw new IllegalArgumentException("Can't find category");
        }
        else{
            return category;
        }
    }

    @Transactional
    public Category updateCategoryName(int categoryId, String name){
        Category category= categoryRepository.findCategoryById(categoryId);
        if (category==null){
            throw new IllegalArgumentException("Can't find category");
        }
        else if (name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException("Category name cannot be empty!");
        }
        else{
            category.setName(name);
            return category;
        }
    }

    @Transactional
    public boolean deleteCategory(int categoryId){
        Category category= categoryRepository.findCategoryById(categoryId);
        if (category==null){
            throw new IllegalArgumentException("Can't find category");
        }
        else{
            categoryRepository.delete(category);
            return true;
        }
    }

}
