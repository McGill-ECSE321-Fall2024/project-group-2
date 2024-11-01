package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.LineItemDto;
import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LineItemRestController {

    @Autowired
    private LineItemService lineItemService;

    @GetMapping("/lineitems/{id}")
    public LineItemDto getLineItem(@PathVariable int id) {
        LineItem lineItem = lineItemService.getLineItem(id);
        return new LineItemDto(lineItem.getId(), lineItem.getQuantity(), lineItem.getPrice(),
                lineItem.getOrder() != null ? lineItem.getOrder().getNumber() : null,
                lineItem.getCart() != null ? lineItem.getCart().getId() : null,
                lineItem.getWishlist() != null ? lineItem.getWishlist().getId() : null);
    }

    @PostMapping("/lineitems/create")
    public LineItemDto createLineItem(@RequestBody LineItemDto lineItemDto) {
        LineItem lineItem = lineItemService.createLineItem(lineItemDto.getQuantity(), lineItemDto.getPrice());
        return new LineItemDto(lineItem.getId(), lineItem.getQuantity(), lineItem.getPrice(), null, null, null);
    }

    @PutMapping("/lineitems/update/{id}")
    public LineItemDto updateLineItem(@PathVariable int id, @RequestBody LineItemDto lineItemDto) {
        LineItem lineItem = lineItemService.updateLineItem(id, lineItemDto.getQuantity(), lineItemDto.getPrice());
        return new LineItemDto(lineItem.getId(), lineItem.getQuantity(), lineItem.getPrice(), null, null, null);
    }

    @DeleteMapping("/lineitems/delete/{id}")
    public boolean deleteLineItem(@PathVariable int id) {
        return lineItemService.deleteLineItem(id);
    }

    @PutMapping("/lineitems/addToCart/{lineItemId}/{cartId}")
    public LineItemDto addToCart(@PathVariable int lineItemId, @PathVariable int cartId) {
        LineItem lineItem = lineItemService.addToCart(lineItemId, cartId);
        return new LineItemDto(lineItem.getId(), lineItem.getQuantity(), lineItem.getPrice(), null, cartId, null);
    }

    @PutMapping("/lineitems/addToWishlist/{lineItemId}/{wishlistId}")
    public LineItemDto addToWishlist(@PathVariable int lineItemId, @PathVariable int wishlistId) {
        LineItem lineItem = lineItemService.addToWishlist(lineItemId, wishlistId);
        return new LineItemDto(lineItem.getId(), lineItem.getQuantity(), lineItem.getPrice(), null, null, wishlistId);
    }

    @PutMapping("/lineitems/removeFromCart/{lineItemId}")
    public LineItemDto removeFromCart(@PathVariable int lineItemId) {
        LineItem lineItem = lineItemService.removeFromCart(lineItemId);
        return new LineItemDto(lineItem.getId(), lineItem.getQuantity(), lineItem.getPrice(), null, null, null);
    }

    @PutMapping("/lineitems/removeFromWishlist/{lineItemId}")
    public LineItemDto removeFromWishlist(@PathVariable int lineItemId) {
        LineItem lineItem = lineItemService.removeFromWishlist(lineItemId);
        return new LineItemDto(lineItem.getId(), lineItem.getQuantity(), lineItem.getPrice(), null, null, null);
    }
}
