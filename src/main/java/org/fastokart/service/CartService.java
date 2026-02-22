package org.fastokart.service;

import org.fastokart.model.Cart;
import org.fastokart.model.CartItem;

import java.util.List;

public interface CartService {
    Cart addToCart(String cartId, Long productId);
    Cart getCart(String cartId);
    List<CartItem> getCartItems(String cartId);
    Cart decreaseCartItem(String cartId, Long productId);
    Cart removeCartItem(String cartId, Long productId);
}
