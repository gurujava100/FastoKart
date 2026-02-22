package org.fastokart.Controller.User.api;

import jakarta.servlet.http.HttpSession;
import org.fastokart.model.Cart;
import org.fastokart.service.CartService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutRestController {
    private CartService cartService;
    @GetMapping
    public Cart getCartItems(HttpSession session) {
        String cartId = (String) session.getAttribute("CART_ID");
        return cartService.getCart(cartId); // returns JSON
    }
}
