package org.fastokart.dto;

import lombok.Data;

@Data
public class WishlistResponseDTO {
    private Long id;          // wishlist id (for remove / move to cart)
    private Long productId;   // useful for navigation

    private String name;
    private Double price;

    private String image;     // image file name

    private Boolean inStock;
}
