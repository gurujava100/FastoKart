package org.fastokart.dto;

import lombok.Data;

@Data
public class BuyNowItem {
    private Long productId;
    private String name;
    private Double price;
    private String  imageName; ;
    private Integer quantity;
}

