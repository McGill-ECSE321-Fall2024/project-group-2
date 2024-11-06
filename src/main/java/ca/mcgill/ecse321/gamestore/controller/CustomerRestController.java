package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.dto.CustomerListDto;
import ca.mcgill.ecse321.gamestore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller to expose CRUD operations for Customer entity.
 */
@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    // Service to handle business logic for customer operations
    @Autowired
    private CustomerService customerService;

    /**
     * Create a new customer.
     *
     * @param requestDto the request body containing customer details
     * @return the created customer as a response DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto requestDto) {
        // Invokes the service to create a new customer based on request data
        Customer customer = customerService.createCustomer(
                requestDto.getUserID(),
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return new CustomerResponseDto(customer); // Returns the created customer as a DTO
    }

    /**
     * Get all customers.
     *
     * @return a list of all customers as response DTOs
     */
    @GetMapping("/customer")
    public CustomerListDto findAllCustomer() {
        // Initializes a list to store response DTOs of all customers
        List<CustomerResponseDto> customer = new ArrayList<CustomerResponseDto>();
        for (Customer model : customerService.getAllCustomers()) {
            customer.add(new CustomerResponseDto(model)); // Adds each customer to the list as a DTO
        }
        return new CustomerListDto(customer); // Returns all customers encapsulated in a list DTO
    }

    /**
     * Get a customer by email.
     *
     * @param email the email of the customer
     * @return the customer with the given email as a response DTO
     */
    @GetMapping("/{email}")
    public CustomerResponseDto getCustomerByEmail(@PathVariable String email) {
        // Finds the customer by email using the service and returns it as a DTO
        Customer customer = customerService.getCustomer(email);
        return new CustomerResponseDto(customer);
    }

    /**
     * Update a customer’s password.
     *
     * @param email the email of the customer to update
     * @param oldPassword the old password of the customer
     * @param newPassword the new password to set
     * @return the updated customer as a response DTO
     */
    @PutMapping("/{email}/updatePassword")
    public CustomerResponseDto updateCustomerPassword(
            @PathVariable String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        // Updates customer password and returns the updated customer as a DTO
        Customer updatedCustomer = customerService.updateCustomerPassword(
                email, oldPassword, newPassword
        );
        return new CustomerResponseDto(updatedCustomer);
    }

    /**
     * Delete a customer by email.
     *
     * @param email the email of the customer to delete
     * @return a success message if deletion is successful
     */
    @DeleteMapping("/{email}")
    public String deleteCustomer(@PathVariable String email) {
        // Calls service to delete customer by email
        customerService.deleteCustomer(email);
        // Returns a confirmation message upon successful deletion
        return "Customer with email " + email + " deleted successfully.";
    }
}


