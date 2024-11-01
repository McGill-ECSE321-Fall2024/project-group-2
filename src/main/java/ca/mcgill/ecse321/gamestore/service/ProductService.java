package ca.mcgill.ecse321.gamestore.service;

import ca.mcgill.ecse321.gamestore.repository.CategoryRepository;
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
    @Autowired
    CategoryRepository categoryRepository;

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
    public Iterable<Product> getAllProduct(){
        return productRepository.findAll();
    }

    @Transactional
    public Iterable<Product> getAllProductWithCategory(int categoryId){
        return productRepository.findAllByCategoryId(categoryId);
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

    @Transactional
    public Product updateProductName(int productId, String name){
        Product product = productRepository.findProductById(productId);
            if (product==null){
                throw new IllegalArgumentException( "Product Not Found!");
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
                throw new IllegalArgumentException( "Product Not Found!");
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
                throw new IllegalArgumentException( "Product Not Found!");
            }
            else if (categoryRepository.findCategoryById(categoryId)==null){
                throw new IllegalArgumentException( "Category not found");
            }
            else{
                product.setCategory(categoryRepository.findCategoryById(categoryId));
                return product;
            }
        }

}
