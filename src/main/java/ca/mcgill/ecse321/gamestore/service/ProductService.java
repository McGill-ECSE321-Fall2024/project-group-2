package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    CategoryService categoryService;
    @Autowired
    LineItemService lineItemService;

    @Transactional
    public Product getProduct(int productId){
        Product product= productRepository.findProductById(productId);
        if (product==null){
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find product with the given Id!");
        }
        else{
            return product;
        }
    }

    @Transactional
    public Product getProductByName (String productName){
        Product product= productRepository.findProductByName(productName);
        if (product==null){
            throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find product with the given name!");
        }
        else{
            return product;
        }
    }

    @Transactional
    public List<Product> getAllProduct(){
        return (List<Product>) productRepository.findAll();
    }

    @Transactional
    public List<Product> getAllProductWithCategory(int categoryId){
        return (List<Product>) productRepository.findAllByCategoryId(categoryId);
    }


    @Transactional
    public Product createProduct(String name, String description, String imageURL, int lineItem_id, int category_id){
        Category category= categoryService.getCategory(category_id);
        LineItem lineItem= lineItemService.getLineItem(lineItem_id);

        if (description==null||description.trim().isEmpty()){
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The description cannot be empty!");
        }
        else if (name==null||name.trim().isEmpty()){
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The name cannot be empty!");
        }
        else if (imageURL==null||imageURL.trim().isEmpty()){
            throw new GameStoreException(HttpStatus.BAD_REQUEST, "The name cannot be empty!");
        }
        else{
            Product product= new Product(name, description, imageURL, lineItem, category);
            productRepository.save(product);
            return product;
        }
    }

    @Transactional
    public Boolean deleteProduct(int productId){
        Product product = productRepository.findProductById(productId);
            if (product==null){
                throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find product with the given Id!");
            }
            else{
                productRepository.delete(product);
                return true;
            }
    }

    @Transactional
    public Product updateProductName(int productId, String name){
        Product product = productRepository.findProductById(productId);
            if (product==null){
                throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find product with the given Id!");
            }
            else if (name == null || name.trim().isEmpty()){
                throw new GameStoreException(HttpStatus.BAD_REQUEST, "The name cannot be empty!");
            }
            else{
                product.setName(name);
                return product;
            }
        }

    @Transactional
    public Product updateProductDescription(int productId, String descrtiption){
        Product product = productRepository.findProductById(productId);
            if (product==null){
                throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find product with the given Id!");
            }
            else if (descrtiption == null || descrtiption.trim().isEmpty()){
                throw new GameStoreException(HttpStatus.BAD_REQUEST, "The description cannot be empty!");
            }
            else{
                product.setDescription(descrtiption);
                return product;
            }
        }


    @Transactional
    public Product updateProductCategory(int productId, int categoryId){
        Product product = productRepository.findProductById(productId);
            if (product==null){
                throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find product with the given Id!");
            }
            else if (categoryRepository.findCategoryById(categoryId)==null){
                throw new GameStoreException(HttpStatus.NOT_FOUND, "Can't find category with the given Id!");
            }
            else{
                product.setCategory(categoryRepository.findCategoryById(categoryId));
                return product;
            }
        }

}
