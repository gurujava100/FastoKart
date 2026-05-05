package org.fastokart.service.impl;

import lombok.RequiredArgsConstructor;
import org.fastokart.dto.WishlistResponseDTO;
import org.fastokart.mapper.WishlistMapper;
import org.fastokart.model.ProductModel;
import org.fastokart.model.WishlistModel;
import org.fastokart.repository.ProductRepository;
import org.fastokart.repository.WishlistRepository;
import org.fastokart.service.CartService;
import org.fastokart.service.WishlistService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl  implements WishlistService {
    private final WishlistRepository wishlistRepo;
    private final ProductRepository productRepo;
    private final CartService cartService;
    public boolean toggle(Long userId, Long productId) {

        Optional<WishlistModel> existing =
                wishlistRepo.findByUserIdAndProductId(userId, productId);

        if (existing.isPresent()) {
            wishlistRepo.delete(existing.get());
            return false; // removed
        }

        WishlistModel w = new WishlistModel();
        w.setUserId(userId);
        w.setProductId(productId);
        w.setCreatedAt(LocalDateTime.now());

        wishlistRepo.save(w);
        return true; // added
    }
    @Override
    public List<WishlistResponseDTO> getUserWishlist(Long userId) {

        List<WishlistModel> list = wishlistRepo.findByUserId(userId);

        return list.stream()
                .map(w -> {
                    ProductModel p = productRepo.findById(w.getProductId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    return WishlistMapper.toDTO(w, p);
                })
                .toList();
    }

    @Override
    public void remove(Long userId, Long wishlistId) {

        WishlistModel w = wishlistRepo.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // 🔥 Security check (VERY IMPORTANT)
        if (!w.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        wishlistRepo.delete(w);   // ✅ actual delete
    }
    @Override
    public void moveToCart(Long userId, Long wishlistId) {

        WishlistModel w = wishlistRepo.findById(wishlistId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        // 🔥 security check
        if (!w.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized");
        }

        Long productId = w.getProductId();

        // 1️⃣ Add to cart
        cartService.addToCart(String.valueOf(userId), productId);

        // 2️⃣ Remove from wishlist
        wishlistRepo.delete(w);
    }
    @Override
    public Long getWishlistCount(Long userId) {
        return wishlistRepo.countByUserId(userId);
    }
}
