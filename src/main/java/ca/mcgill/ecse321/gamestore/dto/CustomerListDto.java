package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class CustomerListDto {
    private List<CustomerResponseDto> customer;

    public CustomerListDto(List<CustomerResponseDto> customer) {
        this.customer = customer;
    }

    public List<CustomerResponseDto> getCustomer() {
        return this.customer;
    }

    public void setCustomer(List<CustomerResponseDto> customer){
        this.customer= customer;
    }
}
