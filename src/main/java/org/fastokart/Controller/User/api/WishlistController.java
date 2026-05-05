package org.fastokart.Controller.User.api;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.fastokart.service.WishlistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {
    private final WishlistService wishlistService;
    @PostMapping("/{productId}")
    public ResponseEntity<?> toggle(@PathVariable Long productId,
                                    HttpSession session) {

        Long userId = (Long) session.getAttribute("USER_ID");
        System.out.println("session id"+userId);
        if (userId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }
        boolean added = wishlistService.toggle(userId, productId);

        return ResponseEntity.ok(Map.of(
                "added", added
        ));
    }
    @GetMapping
    public ResponseEntity<?> getWishlist(HttpSession session) {

        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }

        return ResponseEntity.ok(
                wishlistService.getUserWishlist(userId)
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id,
                                    HttpSession session) {

        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return ResponseEntity.status(401).body("User not logged in");
        }

        wishlistService.remove(userId, id);

        return ResponseEntity.ok("Deleted successfully");
    }
    @PostMapping("/move-to-cart/{wishlistId}")
    public ResponseEntity<?> moveToCart(@PathVariable Long wishlistId,
                                        HttpSession session) {

        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return ResponseEntity.status(401).body(Map.of(
                    "success", false,
                    "message", "User not logged in"
            ));
        }

        wishlistService.moveToCart(userId, wishlistId);

        return ResponseEntity.ok(Map.of(
                "success", true,
                "message", "Moved to cart"
        ));
    }
    @GetMapping("/count")
    public ResponseEntity<?> wishlistCount(HttpSession session) {

        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return ResponseEntity.ok(Map.of("count", 0));
        }

        Long count = wishlistService.getWishlistCount(userId);

        return ResponseEntity.ok(Map.of("count", count));
    }
}
