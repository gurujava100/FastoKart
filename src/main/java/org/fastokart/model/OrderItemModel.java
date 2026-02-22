package org.fastokart.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class OrderItemModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;
}
