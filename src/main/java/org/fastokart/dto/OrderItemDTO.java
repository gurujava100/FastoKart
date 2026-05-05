package org.fastokart.dto;

import lombok.Data;

@Data
public class OrderItemDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
    private String productImage;
}
