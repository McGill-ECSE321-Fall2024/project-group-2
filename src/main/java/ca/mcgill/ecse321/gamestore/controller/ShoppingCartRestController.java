package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.OwnerDto;
import ca.mcgill.ecse321.gamestore.dto.OwnerListDto;
import ca.mcgill.ecse321.gamestore.model.Owner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import ca.mcgill.ecse321.gamestore.dto.ShoppingCartResponseDto;
import ca.mcgill.ecse321.gamestore.dto.ShoppingCartListDto;

import ca.mcgill.ecse321.gamestore.model.ShoppingCart;
import ca.mcgill.ecse321.gamestore.service.ShoppingCartService;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/shoppingcarts")
public class ShoppingCartRestController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @GetMapping("/shoppingcarts")
    public ShoppingCartListDto findAllShoppingCarts() {
        List<ShoppingCartResponseDto> shoppingCart = new ArrayList<ShoppingCartResponseDto>();
        for (ShoppingCart model : shoppingCartService.findAllShoppingCarts()) {
            shoppingCart.add(new ShoppingCartResponseDto(model));
        }
        return new ShoppingCartListDto(shoppingCart);
    }
    @PostMapping(value = { "/create", "/create/" })
    public ShoppingCartResponseDto createShoppingCart(@RequestBody ShoppingCartResponseDto shoppingCartDto) {
        // Create a new shopping cart using the provided DTO
        ShoppingCart shoppingCart = shoppingCartService.createShoppingCart(shoppingCartDto.getCreationDate());
        return new ShoppingCartResponseDto(shoppingCart);
    }


    @GetMapping(value = { "/{id}", "/{id}/" })
    public ShoppingCartResponseDto getShoppingCart(@PathVariable int id) {
        ShoppingCart shoppingCart = shoppingCartService.getShoppingCart(id);
        return new ShoppingCartResponseDto(shoppingCart);
    }


    @DeleteMapping(value = { "/delete/{id}", "/delete/{id}/" })
    public boolean deleteShoppingCart(@PathVariable int id) {
        Boolean deleted = shoppingCartService.deleteShoppingCart(id);
        return deleted;
    }
}
