package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class ProductListDto {
    private List<ProductResponseDto> product;

    public ProductListDto(){
        
    }

    public ProductListDto(List<ProductResponseDto> products) {
        this.product = products;
    }

    public List<ProductResponseDto> getProducts() {
        return this.product;
    }

    public void setProducts(List<ProductResponseDto> products){
        this.product= products;
    }

}
