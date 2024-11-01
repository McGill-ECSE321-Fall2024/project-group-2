package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Category;
import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.model.Product;

public class ProductRequestDto {
    private int productId;
    private String name;
    private String description;
    private LineItem lineItem;
    private Category category;
    
    //CONSTRUCTERS
    public ProductRequestDto(){
    }

    public ProductRequestDto(int productId, String name, String description, LineItem lineItem, Category category){
        this.productId= productId;
        this.name= name;
        this.description= description;
        this.lineItem= lineItem;
        this.category= category;
    }

    public ProductRequestDto(Product product){
        this.productId= product.getId();
        this.name= product.getName();
        this.description= product.getDescription();
        this.lineItem= product.getLineItemOfProduct();
        this.category= product.getCategory();
    }

    //GETTERS
    public int getId(){
        return this.productId;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }

    public LineItem getLineItemOfProduct(){
        return this.lineItem;
    }

    public Category getCategory(){
        return this.category;
    }

    //SETTERS
    public boolean setId(int productId){
        this.productId= productId;
        return true;
    }

    public boolean setName(String name){
        this.name= name;
        return true;
    }

    public boolean setDescription(String description){
        this.description= description;
        return true;
    }

    public boolean setLineItemOfProduct(LineItem lineItem){
        this.lineItem= lineItem;
        return true;
    }

    public boolean setCategory(Category category){
        this.category= category;
        return true;
    }


}
