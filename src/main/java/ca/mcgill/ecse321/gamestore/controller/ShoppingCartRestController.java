package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.OwnerDto;
import ca.mcgill.ecse321.gamestore.dto.OwnerListDto;
import ca.mcgill.ecse321.gamestore.dto.ShoppingCartRequestDto;
import ca.mcgill.ecse321.gamestore.dto.ShoppingCartResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ShoppingCartListDto;
import ca.mcgill.ecse321.gamestore.model.Owner;
import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import ca.mcgill.ecse321.gamestore.service.ShoppingCartService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/")
    public ShoppingCartListDto findAllShoppingCarts() {
        List<ShoppingCartResponseDto> shoppingCartDtos = new ArrayList<>();
        for (ShoppingCart model : shoppingCartService.findAllShoppingCarts()) {
            shoppingCartDtos.add(new ShoppingCartResponseDto(model));
        }
        return new ShoppingCartListDto(shoppingCartDtos);
    }

    @PostMapping(value = { "/create", "/create/" })
    public ShoppingCartResponseDto createShoppingCart(@RequestBody ShoppingCartRequestDto shoppingCartRequestDto) {
        // Create a new shopping cart using the provided DTO

        // Call the service to create the shopping cart using the provided creation date
        ShoppingCart createdShoppingCart = shoppingCartService.createShoppingCart(shoppingCartRequestDto.getCreationDate());

        // Return the response DTO
        return new ShoppingCartResponseDto(createdShoppingCart);
    }

    @GetMapping(value = { "/{id}", "/{id}/" })
    public ShoppingCartResponseDto getShoppingCart(@PathVariable int id) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(id);
        return new ShoppingCartResponseDto(shoppingCart);
    }
}

