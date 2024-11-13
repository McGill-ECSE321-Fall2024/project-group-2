package ca.mcgill.ecse321.gamestore.dto;

import java.util.List;

public class CustomerListDto {
    private List<CustomerResponseDto> customers;  // Changed the variable name to 'customers'

    // No-argument constructor
    public CustomerListDto() {
    }

    // Constructor
    public CustomerListDto(List<CustomerResponseDto> customers) {
        this.customers = customers;
    }

    // Getter
    public List<CustomerResponseDto> getCustomers() {  // Changed to 'getCustomers'
        return this.customers;
    }

    // Setter
    public void setCustomers(List<CustomerResponseDto> customers) {  // Changed to 'setCustomers'
        this.customers = customers;
    }
}


