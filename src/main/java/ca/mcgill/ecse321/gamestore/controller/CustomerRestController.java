package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.service.CustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to expose CRUD operations for Customer entity.
 */
@RestController
@RequestMapping("/customers")
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    /**
     * Create a new customer.
     *
     * @param requestDto the request body containing customer details
     * @return the created customer as a response DTO
     */
    @PostMapping
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto requestDto) {
        Customer customer = customerService.createCustomer(
                requestDto.getUserID(),
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return new CustomerResponseDto(customer);
    }

    /**
     * Get all customers.
     *
     * @return a list of all customers as response DTOs
     */
    @GetMapping
    public List<CustomerResponseDto> getAllCustomers() {
        return customerService.getAllCustomers().stream()
                .map(CustomerResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * Get a customer by email.
     *
     * @param email the email of the customer
     * @return the customer with the given email as a response DTO
     */
    @GetMapping("/{email}")
    public CustomerResponseDto getCustomerByEmail(@PathVariable String email) {
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
        customerService.deleteCustomer(email);
        return "Customer with email " + email + " deleted successfully.";
    }
}

