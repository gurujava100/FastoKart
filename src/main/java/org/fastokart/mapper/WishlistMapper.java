package org.fastokart.mapper;

import org.fastokart.dto.WishlistResponseDTO;
import org.fastokart.model.ProductModel;
import org.fastokart.model.WishlistModel;

public class WishlistMapper {
    public static WishlistResponseDTO toDTO(WishlistModel w, ProductModel p) {

        WishlistResponseDTO dto = new WishlistResponseDTO();

        dto.setId(w.getId());
        dto.setProductId(p.getId());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        dto.setImage(p.getImageName());

        // optional
        dto.setInStock(p.getActive());

        return dto;
    }
}
