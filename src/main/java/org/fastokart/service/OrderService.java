package org.fastokart.service;

import org.fastokart.dto.BuyNowItem;
import org.fastokart.dto.OrderResponseDTO;
import org.fastokart.enm.OrderStatus;
import org.fastokart.model.OrderModel;

import java.util.List;

public interface OrderService {
    OrderModel createDirectOrder(BuyNowItem item,
                                 Long addressId,
                                 String payment);
    public List<OrderResponseDTO> getOrders(Long userId);
    public List<OrderResponseDTO> getAllOrders();
    void updateStatus(Long orderId, OrderStatus status);
}
