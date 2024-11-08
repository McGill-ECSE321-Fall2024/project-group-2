package ca.mcgill.ecse321.gamestore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
import jakarta.transaction.Transactional;

@Service
public class CategoryService {
    
    @Autowired
    CategoryRepository categoryRepository;

    @Transactional
    public Category createCategory (String name){
        if (name == null || name.trim().isEmpty()) {
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The name cannot be empty!");
        }
        Category category = new Category(name);
        return categoryRepository.save(category);
    }

    @Transactional
    public Category getCategory (int categoryId){
        Category category= categoryRepository.findCategoryById(categoryId);
        if (category==null){
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find category with the given Id!");
        }
        else{
            return category;
        }
    }

    @Transactional
    public List<Category> getAllCategory (){
        return (List<Category>) categoryRepository.findAll();
    }

    @Transactional
    public Category updateCategoryName(int categoryId, String name){
        Category category= categoryRepository.findCategoryById(categoryId);
        if (category==null){
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find category with the given Id!");
        }
        else if (name == null || name.trim().isEmpty()){
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The name cannot be empty!");
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
                throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find category with the given Id!");
            }
            else{
                categoryRepository.delete(category);
                return true;
            }
    }

}
