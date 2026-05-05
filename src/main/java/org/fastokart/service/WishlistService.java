package org.fastokart.service;

import org.fastokart.dto.WishlistResponseDTO;

import java.util.List;

public interface WishlistService {
    boolean toggle(Long userId, Long productId);

    // 🔹 Check if product exists in wishlist
  //  boolean exists(Long userId, Long productId);

    // 🔹 Get all wishlist items for a user
    List<WishlistResponseDTO> getUserWishlist(Long userId);

    // 🔹 Remove specific wishlist item
    void remove(Long userId, Long wishlistId);

    // 🔹 Move item from wishlist → cart
   void moveToCart(Long wishlistId, Long userId);
    Long getWishlistCount(Long userId);

}
