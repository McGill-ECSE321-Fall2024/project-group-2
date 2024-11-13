package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.ProductListDto;
import ca.mcgill.ecse321.gamestore.dto.ProductRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ProductResponseDto;
import ca.mcgill.ecse321.gamestore.exception.GameStoreException;
import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.service.ProductService;

@RestController
public class ProductRestController {
    
    @Autowired
    private ProductService productService;

    /**
     * Create a new product.
     *
     * @param productDto The product to create.
     * @return The created product.
     */
    @PostMapping("/product")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productDto){
         try {
            Product createdProduct = productService.createProduct(productDto.getName(), productDto.getDescription(), productDto.getLineItem(), productDto.getCategory());
            return new ResponseEntity<>(new ProductResponseDto(createdProduct), HttpStatus.CREATED);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    /**
     * Return the product with the given ID.
     *
     * @param productId The primary key of the product to find.
     * @return The product with the given ID.
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable int productId){
        try {
            Product createdProduct = productService.getProduct(productId);
            return new ResponseEntity<>(new ProductResponseDto(createdProduct), HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }



    /**
     * Return All the products.
     *
     * @return All the products with the given ID.
     */
    @GetMapping("/product")
    public ResponseEntity<ProductListDto> getAllProduct(){
        List<ProductResponseDto> products = new ArrayList<>();
        for (Product product : productService.getAllProduct()) {
            products.add(new ProductResponseDto(product));
        } return new ResponseEntity<>(new ProductListDto(products), HttpStatus.OK);
    }
    /**
     * Return All the products with the given category.
     *
     * @param CategoryId The category id.
     * @return A list of products belonging to the given category id.
     */
    @GetMapping("/product/category/{categoryId}")
    public ResponseEntity<ProductListDto> getAllProductWithCategory(@PathVariable int categoryId){
        List<ProductResponseDto> products = new ArrayList<>();
        for (Product product : productService.getAllProductWithCategory(categoryId)) {
            products.add(new ProductResponseDto(product));
        } return new ResponseEntity<>(new ProductListDto(products), HttpStatus.OK);
    }

    /**
     * Delete the product with the given ID.
     *
     * @param productId The primary key of the product to delete.
     * @return Boolean indicating whether the product was successfully deleted or not.
     */
    @DeleteMapping("/product/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable int productId){
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>("Product was deleted successfully.", HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }


    /**
     * Update the product name with the given ID.
     *
     * @param productId The primary key of the product to update.
     * @param newName The new name of the product to update.
     * @return The updated product.
     */
    @PutMapping("/product/name/{productId}")
    public ResponseEntity<?> updateProductName(@PathVariable int productId, @RequestParam String newName){
        try {
            Product product = productService.updateProductName(productId, newName);
            return new ResponseEntity<>(new ProductResponseDto(product), HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    /**
     * Update the product description with the given ID.
     *
     * @param productId The primary key of the product to update.
     * @param newDescription The new name of the product to update.
     * @return The updated product.
     */
    @PutMapping("/product/description/{productId}")
    public ResponseEntity<?> updateProductDescription(@PathVariable int productId, @RequestParam String newDescription){
        try {
            Product product = productService.updateProductDescription(productId, newDescription);
            return new ResponseEntity<>(new ProductResponseDto(product), HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }

    /**
     * Update the product category with the given ID.
     *
     * @param productId The primary key of the product to update.
     * @param newCategory The new category of the product to update.
     * @return The updated product.
     */
    @PutMapping("/product/{productId}/category/{newCategoryId}")
    public ResponseEntity<?> updateProductCategory(@PathVariable int productId, @PathVariable int newCategoryId){
        try {
            Product product = productService.updateProductCategory(productId, newCategoryId);
            return new ResponseEntity<>(new ProductResponseDto(product), HttpStatus.OK);
        } catch (GameStoreException e) {
            return new ResponseEntity<>(e.getMessage(), e.getStatus());
        }
    }



}
