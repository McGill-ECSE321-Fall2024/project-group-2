package ca.mcgill.ecse321.gamestore.dto;

import ca.mcgill.ecse321.gamestore.model.Product;

public class ProductResponseDto {
    private int productId;
    private String name;
    private String description;
    private String imageURL;
    private int lineItem_id;
    private int category_id;
    
    //CONSTRUCTERS
    public ProductResponseDto(){
    }

    public ProductResponseDto(int productId, String name, String description, String imageURL, int lineItem_id, int category_id){
        this.productId= productId;
        this.name= name;
        this.imageURL= imageURL;
        this.description= description;
        this.lineItem_id= lineItem_id;
        this.category_id= category_id;
    }

    public ProductResponseDto(Product product){
        this.productId= product.getId();
        this.name= product.getName();
        this.imageURL= product.getImage();
        this.description= product.getDescription();
        this.lineItem_id= product.getLineItemOfProduct().getId();
        this.category_id= product.getCategory().getId();
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

    public String getImageURL(){
        return this.imageURL;
    }

    public int getLineItem_id(){
        return this.lineItem_id;
    }

    public int getCategory_id(){
        return this.category_id;
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

    public boolean setImageURL(String imageURL){
        this.imageURL= imageURL;
        return true;
    }

    public boolean setLineItem_id(int lineItem_id){
        this.lineItem_id= lineItem_id;
        return true;
    }

    public boolean setCategory_id(int category_id){
        this.category_id= category_id;
        return true;
    }


}