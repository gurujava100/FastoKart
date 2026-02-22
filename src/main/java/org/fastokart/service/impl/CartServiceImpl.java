package org.fastokart.service.impl;

import jakarta.transaction.Transactional;
import org.fastokart.model.Cart;
import org.fastokart.model.CartItem;
import org.fastokart.model.ProductModel;
import org.fastokart.repository.CartItemRepository;
import org.fastokart.repository.CartRepository;
import org.fastokart.repository.ProductRepository;
import org.fastokart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Cart addToCart(String cartId, Long productId) {

        if (cartId == null || cartId.isBlank()) {
            throw new RuntimeException("cartId missing");
        }

        // get or create cart
        Cart cart = cartRepository.findByCartId(cartId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setCartId(cartId);
                    return cartRepository.save(newCart);
                });

        // get product
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // check existing item
        CartItem existingItem = cartItemRepository.findByCartAndProduct(cart, product)
                .orElse(null);

        if (existingItem != null) {

            existingItem.setQuantity(existingItem.getQuantity() + 1);
            cartItemRepository.save(existingItem);

        } else {

            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(1);
            newItem.setPrice(product.getPrice());

            cartItemRepository.save(newItem);  // ✅ only this
        }

        return getCart(cartId);
        // reload fresh state
    }

    @Override
    @Transactional
    public Cart getCart(String cartId) {

        Cart cart = cartRepository.findByCartId(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        // load cart items
        cart.getItems().forEach(item -> {
            item.getProduct().getId();   // 🔥 load product also
        });

        return cart;
    }

    @Override
    public List<CartItem> getCartItems(String cartId) {
        return cartItemRepository.findItemsWithProduct(cartId);
    }
    @Override
    @Transactional
    public Cart decreaseCartItem(String cartId, Long productId) {
        Cart cart = getCart(cartId);
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);
        if (item != null) {
            item.setQuantity(item.getQuantity() - 1);
            if (item.getQuantity() <= 0) {
                cartItemRepository.delete(item);
            } else {
                cartItemRepository.save(item);
            }
        }
        return getCart(cartId);
    }
     @Override
    @Transactional
    public Cart removeCartItem(String cartId, Long productId) {
        Cart cart = getCart(cartId);
        ProductModel product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem item = cartItemRepository.findByCartAndProduct(cart, product).orElse(null);
        if (item != null) {
            cartItemRepository.delete(item);
        }
        return getCart(cartId);
    }

}
