package org.fastokart.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import org.fastokart.enm.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderResponseDTO {
    private Long orderId;

    private String customerName;
    private String phone;
    private String address;

    private String paymentMethod;
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PLACED;

    private Double totalAmount;
    private LocalDateTime orderDate;

    private List<OrderItemDTO> items;
    private LocalDateTime deliveredAt;
}
