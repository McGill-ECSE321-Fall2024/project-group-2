package ca.mcgill.ecse321.gamestore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.repository.ProductRepository;
import jakarta.transaction.Transactional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public Product getProduct(int productId){
        Product product= productRepository.findProductById(productId);
        if (product==null){
            throw new IllegalArgumentException( "Product Not Found!");
        }
        else{
            return product;
        }
    }

    @Transactional
    public Product createProduct(String name, String description, LineItem lineItem, Category category){
        if (category==null){
            throw new IllegalArgumentException( "Category cannot be null!");
        }
        else if (lineItem==null){
            throw new IllegalArgumentException( "LineItem cannot be null!");
        }
        else if (description==null||description.trim().isEmpty()){
            throw new IllegalArgumentException( "Description cannot be empty!");
        }
        else if (name==null||name.trim().isEmpty()){
            throw new IllegalArgumentException( "Name cannot be empty!");
        }
        else{
            Product product= new Product(name, description, lineItem, category);
            productRepository.save(product);
            return product;
        }
    }

    @Transactional
    public Boolean deleteProduct(int productId){
        Product product = productRepository.findProductById(productId);
        try{
            if (product==null){
                throw new IllegalArgumentException( "Product Not Found!");
            }
            else{
                productRepository.delete(product);
                return true;
            }
        }
        catch (Exception e){
            return false;
        }
    }

}
