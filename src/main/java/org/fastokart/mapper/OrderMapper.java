package org.fastokart.mapper;

import org.fastokart.dto.OrderItemDTO;
import org.fastokart.dto.OrderResponseDTO;
import org.fastokart.model.OrderItemModel;
import org.fastokart.model.OrderModel;
import org.fastokart.model.ProductModel;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderResponseDTO toDTO(OrderModel order) {

        OrderResponseDTO dto = new OrderResponseDTO();

        dto.setOrderId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setPhone(order.getPhone());
        dto.setAddress(order.getAddress());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setOrderDate(order.getOrderDate());
        dto.setDeliveredAt(order.getDeliveredAt());

        // 🔥 Base URL (dynamic)
        String baseUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .build()
                .toUriString();

        // ✅ Map items safely
        List<OrderItemDTO> items = order.getItems() == null
                ? List.of()
                : order.getItems().stream()
                  .map(item -> mapItem(item, baseUrl))
                  .collect(Collectors.toList());

        dto.setItems(items);

        return dto;
    }

    // 🔥 Separate method for clean code
    private static OrderItemDTO mapItem(OrderItemModel item, String baseUrl) {

        OrderItemDTO dto = new OrderItemDTO();

        dto.setQuantity(item.getQuantity());
        dto.setPrice(item.getPrice());

        ProductModel product = item.getProduct();

        if (product != null) {

            dto.setProductId(product.getId());
            dto.setProductName(product.getName());

            String imageName = product.getImageName();

            // ✅ Safe image handling
            if (imageName != null && !imageName.isBlank()) {
                dto.setProductImage(baseUrl + "/images/" + imageName);
            } else {
                dto.setProductImage(baseUrl + "/images/default.png");
            }
        }

        return dto;
    }
}