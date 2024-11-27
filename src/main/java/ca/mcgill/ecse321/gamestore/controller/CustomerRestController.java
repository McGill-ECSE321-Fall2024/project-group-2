package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerResponseDto;
import ca.mcgill.ecse321.gamestore.dto.CustomerListDto;
import ca.mcgill.ecse321.gamestore.model.Customer;
import ca.mcgill.ecse321.gamestore.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller to expose CRUD operations for Customer entity.
 */
@RestController
public class CustomerRestController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // POST MAPPINGS
    @PostMapping(value = { "/customer", "/customer/" })
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDto createCustomer(@RequestBody CustomerRequestDto requestDto) {
        Customer customer = customerService.createCustomer(
                requestDto.getUserID(),
                requestDto.getName(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );
        return new CustomerResponseDto(customer);
    }

    // GET MAPPINGS
    @GetMapping(value = { "/customers", "/customers/" })
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDto getAllCustomers() {
        List<CustomerResponseDto> customers = customerService.getAllCustomers().stream()
                .map(CustomerResponseDto::new)
                .collect(Collectors.toList());
        return new CustomerListDto(customers);
    }

    @GetMapping(value = { "/customer/{email}", "/customer/{email}/" })
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto getCustomerByEmail(@PathVariable("email") String email) {
        Customer customer = customerService.getCustomer(email);
        return new CustomerResponseDto(customer);
    }

    // PUT MAPPINGS
    @PutMapping(value = { "/customer/{email}/updatePassword", "/customer/{email}/updatePassword/" })
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto updateCustomerPassword(
            @PathVariable("email") String email,
            @RequestParam String oldPassword,
            @RequestParam String newPassword) {
        Customer updatedCustomer = customerService.updateCustomerPassword(
                email, oldPassword, newPassword
        );
        return new CustomerResponseDto(updatedCustomer);
    }

    // DELETE MAPPINGS
    @DeleteMapping(value = { "/customer/{email}", "/customer/{email}/" })
    @ResponseStatus(HttpStatus.OK)
    public String deleteCustomer(@PathVariable("email") String email) {
        customerService.deleteCustomer(email);
        return "Customer with email " + email + " deleted successfully.";
    }

    // Additional Method Based on Original Code (Not Removed)
    /**
     * Find all customers.
     *
     * @return a list of all customers encapsulated in a CustomerListDto
     */
    @GetMapping(value = { "/customer", "/customer/" })
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDto findAllCustomer() {
        List<CustomerResponseDto> customerList = customerService.getAllCustomers().stream()
                .map(CustomerResponseDto::new)
                .collect(Collectors.toList());
        return new CustomerListDto(customerList);
    }

    @PostMapping("/customer/login")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponseDto customerLogin(@RequestParam String email, @RequestParam String password) {
        Customer customer = customerService.getCustomer(email);

        // Check if the customer exists
        if (customer == null) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // Validate the password
        if (!passwordEncoder.matches(password, customer.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password.");
        }

        // Return customer details without the password
        return new CustomerResponseDto(customer);
    }

}



