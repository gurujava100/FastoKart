package org.fastokart.Controller.api;

import jakarta.servlet.http.HttpSession;
import org.fastokart.model.Cart;
import org.fastokart.model.CartItem;
import org.fastokart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartRestController {
    @Autowired
    private CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Integer> addToCart(
            @RequestParam String cartId,
            @RequestParam Long productId) {

        // add product into SAME cart from browser
        Cart cart = cartService.addToCart(cartId, productId);

        int cartCount = cart.getItems()
                .stream()
                .mapToInt(CartItem::getQuantity)
                .sum();

        return ResponseEntity.ok(cartCount);
    }
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> getCart(@PathVariable String cartId) {
        return ResponseEntity.ok(cartService.getCart(cartId));
    }
    //Decrese quantity
    @PostMapping("/decrease")
    public ResponseEntity<Integer> decreaseQty(
            @RequestParam String cartId,
            @RequestParam Long productId) {

        Cart cart = cartService.decreaseCartItem(cartId, productId);
        int cartCount = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
        return ResponseEntity.ok(cartCount);
    }
    //Remove quantity
    @DeleteMapping("/remove")
    public ResponseEntity<Integer> removeItem(
            @RequestParam String cartId,
            @RequestParam Long productId) {

        Cart cart = cartService.removeCartItem(cartId, productId);
        int cartCount = cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
        return ResponseEntity.ok(cartCount);
    }

}
