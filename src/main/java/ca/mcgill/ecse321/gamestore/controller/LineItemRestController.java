package ca.mcgill.ecse321.gamestore.controller;

import ca.mcgill.ecse321.gamestore.dto.LineItemRequestDto;
import ca.mcgill.ecse321.gamestore.dto.LineItemResponseDto;
import ca.mcgill.ecse321.gamestore.model.LineItem;
import ca.mcgill.ecse321.gamestore.service.LineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/lineitems")
public class LineItemRestController {

    @Autowired
    private LineItemService lineItemService;

    /**
     * Get a LineItem by its ID.
     *
     * @param id The ID of the LineItem.
     * @return LineItemResponseDto containing details of the LineItem.
     */
    @GetMapping("/{id}")
    public ResponseEntity<LineItemResponseDto> getLineItem(@PathVariable int id) {
        LineItem lineItem = lineItemService.getLineItem(id);
        return ResponseEntity.ok(new LineItemResponseDto(lineItem));
    }

    /**
     * Create a new LineItem.
     *
     * @param requestDto The LineItemRequestDto containing quantity, price, and optionally cartId and wishlistId.
     * @return LineItemResponseDto with the created LineItem's details.
     */
    @PostMapping
    public ResponseEntity<LineItemResponseDto> createLineItem(@RequestBody LineItemRequestDto requestDto) {
        LineItem lineItem = lineItemService.createLineItem(requestDto.getQuantity(), requestDto.getPrice());

        // Optionally add to cart or wishlist
        if (requestDto.getCartId() != null) {
            lineItem = lineItemService.addToCart(lineItem.getId(), requestDto.getCartId());
        }
        if (requestDto.getWishlistId() != null) {
            lineItem = lineItemService.addToWishlist(lineItem.getId(), requestDto.getWishlistId());
        }

        return ResponseEntity.ok(new LineItemResponseDto(lineItem));
    }

    /**
     * Update the quantity of an existing LineItem.
     *
     * @param id The ID of the LineItem.
     * @param quantity The new quantity to be set.
     * @return LineItemResponseDto with the updated LineItem's details.
     */
    @PutMapping("/{id}/quantity")
    public ResponseEntity<LineItemResponseDto> updateLineItemQuantity(@PathVariable int id, @RequestBody int quantity) {
        LineItem lineItem = lineItemService.updateLineItemQuantity(id, quantity);
        return ResponseEntity.ok(new LineItemResponseDto(lineItem));
    }

    /**
     * Update the price of an existing LineItem.
     *
     * @param id The ID of the LineItem.
     * @param price The new price to be set.
     * @return LineItemResponseDto with the updated LineItem's details.
     */
    @PutMapping("/{id}/price")
    public ResponseEntity<LineItemResponseDto> updateLineItemPrice(@PathVariable int id, @RequestBody double price) {
        LineItem lineItem = lineItemService.updateLineItemPrice(id, price);
        return ResponseEntity.ok(new LineItemResponseDto(lineItem));
    }

    /**
     * Delete a LineItem by its ID.
     *
     * @param id The ID of the LineItem to delete.
     * @return A boolean indicating the success of the deletion.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteLineItem(@PathVariable int id) {
        boolean deleted = lineItemService.deleteLineItem(id);
        return ResponseEntity.ok(deleted);
    }

    /**
     * Add a LineItem to a ShoppingCart.
     *
     * @param lineItemId The ID of the LineItem.
     * @param cartId The ID of the ShoppingCart to add to.
     * @return LineItemResponseDto with updated details of the LineItem.
     */
    @PutMapping("/{lineItemId}/addToCart/{cartId}")
    public ResponseEntity<LineItemResponseDto> addToCart(@PathVariable int lineItemId, @PathVariable int cartId) {
        LineItem lineItem = lineItemService.addToCart(lineItemId, cartId);
        return ResponseEntity.ok(new LineItemResponseDto(lineItem));
    }

    /**
     * Add a LineItem to a WishList.
     *
     * @param lineItemId The ID of the LineItem.
     * @param wishlistId The ID of the WishList to add to.
     * @return LineItemResponseDto with updated details of the LineItem.
     */
    @PutMapping("/{lineItemId}/addToWishlist/{wishlistId}")
    public ResponseEntity<LineItemResponseDto> addToWishlist(@PathVariable int lineItemId, @PathVariable int wishlistId) {
        LineItem lineItem = lineItemService.addToWishlist(lineItemId, wishlistId);
        return ResponseEntity.ok(new LineItemResponseDto(lineItem));
    }

    /**
     * Remove a LineItem from its associated ShoppingCart.
     *
     * @param lineItemId The ID of the LineItem to remove from the cart.
     * @return LineItemResponseDto with updated details of the LineItem.
     */
    @PutMapping("/{lineItemId}/removeFromCart")
    public ResponseEntity<LineItemResponseDto> removeFromCart(@PathVariable int lineItemId) {
        LineItem lineItem = lineItemService.removeFromCart(lineItemId);
        return ResponseEntity.ok(new LineItemResponseDto(lineItem));
    }

    /**
     * Remove a LineItem from its associated WishList.
     *
     * @param lineItemId The ID of the LineItem to remove from the wishlist.
     * @return LineItemResponseDto with updated details of the LineItem.
     */
    @PutMapping("/{lineItemId}/removeFromWishlist")
    public ResponseEntity<LineItemResponseDto> removeFromWishlist(@PathVariable int lineItemId) {
        LineItem lineItem = lineItemService.removeFromWishlist(lineItemId);
        return ResponseEntity.ok(new LineItemResponseDto(lineItem));
    }
}
