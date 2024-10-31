package ca.mcgill.ecse321.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321.gamestore.dto.ProductDto;
import ca.mcgill.ecse321.gamestore.model.Product;
import ca.mcgill.ecse321.gamestore.service.ProductService;

@RestController
public class ProductController {
    
    @Autowired
    private ProductService productService;

    /**
     * Create a new product.
     *
     * @param productDto The product to create.
     * @return The created product.
     */
    @PostMapping(value= {"/product/create", "/product/create/"})
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        Product createdProduct = productService.createProduct(productDto.getName(), productDto.getDescription(), productDto.getLineItemOfProduct(),productDto.getCategory());
        return new ProductDto(createdProduct);
    }

    /**
     * Return the product with the given ID.
     *
     * @param productId The primary key of the product to find.
     * @return The product with the given ID.
     */
    @GetMapping(value={"/product/{productId}", "/product/{productId}/"})
    public ProductDto getProduct(@PathVariable int productId){
        Product product= productService.getProduct(productId);
        return new ProductDto(product);
    }

    /**
     * Delete the product with the given ID.
     *
     * @param productDto The primary key of the product to delete.
     * @return Boolean indicating whether the product has been sucessfully deleted or not.
     */
    @PostMapping(value= {"/product/delete", "/product/delete/"})
    public boolean deleteProduct(@RequestBody ProductDto productDto){
        boolean deleted= productService.deleteProduct(productDto.getId());
        return deleted;
    }

}
