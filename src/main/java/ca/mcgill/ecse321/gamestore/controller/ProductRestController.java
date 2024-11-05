package ca.mcgill.ecse321.gamestore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.ProductListDto;
import ca.mcgill.ecse321.gamestore.dto.ProductRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ProductResponseDto;
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
    @PostMapping(value= {"/product/create", "/product/create/"})
    public ProductResponseDto createProduct(@RequestBody ProductRequestDto productDto){
        Product createdProduct = productService.createProduct(productDto.getName(), productDto.getDescription(), productDto.getLineItemOfProduct(),productDto.getCategory());
        return new ProductResponseDto(createdProduct);
    }

    /**
     * Return the product with the given ID.
     *
     * @param productId The primary key of the product to find.
     * @return The product with the given ID.
     */
    @GetMapping(value={"/product/{productId}", "/product/{productId}/"})
    public ProductResponseDto getProduct(@PathVariable int productId){
        Product product= productService.getProduct(productId);
        return new ProductResponseDto(product);
    }



    /**
     * Return All the products.
     *
     * @return All the products with the given ID.
     */
    @GetMapping(value={"/product", "/product/"})
    public ProductListDto getAllProduct(){
        List<ProductResponseDto> products = new ArrayList<ProductResponseDto>();
        for (Product product : productService.getAllProduct()) {
            products.add(new ProductResponseDto(product));
        } return new ProductListDto(products);}

    /**
     * Return All the products with the given category.
     *
     * @param CategoryId The category id.
     * @return A list of products belonging to the given category id.
     */
    @GetMapping(value={"/product/category/{categoryId}", "/product/category/{categoryId}/"})
    public ProductListDto getAllProductWithCategory(@PathVariable int CategoryId){
        List<ProductResponseDto> products = new ArrayList<ProductResponseDto>();
        for (Product product : productService.getAllProductWithCategory(CategoryId)) {
            products.add(new ProductResponseDto(product));
        } return new ProductListDto(products);}

    /**
     * Delete the product with the given ID.
     *
     * @param productId The primary key of the product to delete.
     * @return Boolean indicating whether the product was successfully deleted or not.
     */
    @PostMapping(value= {"/product/{productId}/delete", "/product/{productId}/delete/"})
    public boolean deleteProduct(@PathVariable int productId){
        boolean deleted= productService.deleteProduct(productId);
        return deleted;
    }


    /**
     * Update the product name with the given ID.
     *
     * @param productId The primary key of the product to update.
     * @param newName The new name of the product to update.
     * @return The updated product.
     */
    @PostMapping(value= {"/product/{productId}/name/{newName}", "/product/{productId}/name/{newName}/"})
    public ProductResponseDto updateProductName(@PathVariable int productId, @PathVariable String newName){
        Product product= productService.getProduct(productId);
        productService.updateProductName(productId,newName);
        return new ProductResponseDto(product);
    }

    /**
     * Update the product description with the given ID.
     *
     * @param productId The primary key of the product to update.
     * @param newDescription The new name of the product to update.
     * @return The updated product.
     */
    @PostMapping(value= {"/product/{productId}/description/{newDescription}", "/product/{productId}/description/{newDescription}/"})
    public ProductResponseDto updateProductDescription(@PathVariable int productId, @PathVariable String newDescription){
        Product product= productService.getProduct(productId);
        productService.updateProductDescription(productId,newDescription);
        return new ProductResponseDto(product);
    }

    /**
     * Update the product category with the given ID.
     *
     * @param productId The primary key of the product to update.
     * @param newCategory The new category of the product to update.
     * @return The updated product.
     */
    @PostMapping(value= {"/product/{productId}/category/{newCategory}", "/product/{productId}/category/{newCategory}/"})
    public ProductResponseDto updateProductCategory(@PathVariable int productId, @PathVariable int newCategory){
        Product product= productService.getProduct(productId);
        productService.updateProductCategory(productId,newCategory);
        return new ProductResponseDto(product);
    }



}
