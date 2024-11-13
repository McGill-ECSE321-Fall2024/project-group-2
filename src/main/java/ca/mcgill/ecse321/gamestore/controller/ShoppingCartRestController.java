package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.ShoppingCartRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ShoppingCartResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ShoppingCartListDto;
import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import ca.mcgill.ecse321.gamestore.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ShoppingCartRestController {

    // Injecting the ShoppingCartService to manage shopping cart operations
    @Autowired
    private ShoppingCartService shoppingCartService;

    // Endpoint to retrieve all shopping carts
    @GetMapping("/shoppingcart")
    public ShoppingCartListDto findAllShoppingCarts() {
        // Create a list to hold ShoppingCartResponseDto objects
        List<ShoppingCartResponseDto> shoppingCartDtos = new ArrayList<>();

        // Loop through all ShoppingCart models and convert them to DTOs
        for (ShoppingCart model : shoppingCartService.findAllShoppingCarts()) {
            shoppingCartDtos.add(new ShoppingCartResponseDto(model));
        }

        // Return the list of ShoppingCartResponseDto wrapped in ShoppingCartListDto
        return new ShoppingCartListDto(shoppingCartDtos);
    }

    // Endpoint to create a new shopping cart
    @PostMapping(value = { "/shoppingcart", "/shoppingcart/" })
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingCartResponseDto createShoppingCart(@RequestBody ShoppingCartRequestDto shoppingCartRequestDto) {
        // Create a new shopping cart using the provided DTO
        // Call the service to create the shopping cart using the provided creation date
        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCartRequestDto.getCreationDate());

        // Return the response DTO representing the created shopping cart
        return new ShoppingCartResponseDto(createdShoppingCart);
    }

    // Endpoint to retrieve a specific shopping cart by ID
    @GetMapping(value = { "/shoppingcart/{id}", "/shoppingcart/{id}/" })
    public ShoppingCartResponseDto getShoppingCart(@PathVariable int id) {
        // Fetch the ShoppingCart model using the provided ID
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(id);

        // Return the corresponding ShoppingCartResponseDto
        return new ShoppingCartResponseDto(shoppingCart);
    }
}
