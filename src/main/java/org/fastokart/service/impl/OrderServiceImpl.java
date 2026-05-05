package org.fastokart.service.impl;

import org.fastokart.dto.BuyNowItem;
import org.fastokart.dto.OrderResponseDTO;
import org.fastokart.enm.OrderStatus;
import org.fastokart.mapper.OrderMapper;
import org.fastokart.model.AddressModel;
import org.fastokart.model.OrderItemModel;
import org.fastokart.model.OrderModel;
import org.fastokart.model.ProductModel;
import org.fastokart.repository.AddressRepository;
import org.fastokart.repository.OrderRepository;
import org.fastokart.repository.ProductRepository;
import org.fastokart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Override
    public OrderModel createDirectOrder(BuyNowItem item,
                                        Long addressId,
                                  String payment){
        ProductModel product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        AddressModel address = addressRepository.findById(addressId)
                .orElseThrow(() -> new RuntimeException("Address not found"));
        double productPrice = product.getPrice();
        int qty = item.getQuantity();
        double total = productPrice * qty;

        OrderModel order = new OrderModel();
        order.setCustomerName(address.getName());
        order.setPhone(address.getPhone());
        order.setAddress(address.getName()+ ", " + address.getCity() + " - " + address.getPincode()+ ", " + address.getState()+ ", " + address.getLandmark());
        order.setPaymentMethod(payment);
        order.setStatus(OrderStatus.valueOf("PLACED"));
        order.setTotalAmount(total);
        order.setOrderDate(LocalDateTime.now());

        OrderItemModel orderItem = new OrderItemModel();
        orderItem.setProduct(product);
        orderItem.setQuantity(qty);
        orderItem.setPrice(productPrice);
        orderItem.setOrder(order);

        order.setItems(List.of(orderItem));

        return orderRepository.save(order);
    }
    public List<OrderResponseDTO> getOrders(Long userId) {

        List<OrderModel> orders = orderRepository.findAll(); // or by userId

        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }
    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderMapper::toDTO)   // ✅ correct
                .toList();
    }
    @Override
    public void updateStatus(Long orderId, OrderStatus newStatus) {

        OrderModel order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderStatus currentStatus = order.getStatus();

        // ❌ Prevent update if cancelled or delivered
        if (currentStatus == OrderStatus.CANCELLED || currentStatus == OrderStatus.DELIVERED) {
            throw new RuntimeException("Order already closed");
        }

        // 🔥 VALID FLOW CHECK
        boolean valid = switch (currentStatus) {
            case PLACED -> newStatus == OrderStatus.CONFIRMED || newStatus == OrderStatus.CANCELLED;
            case CONFIRMED -> newStatus == OrderStatus.PACKED;
            case PACKED -> newStatus == OrderStatus.SHIPPED;
            case SHIPPED -> newStatus == OrderStatus.OUT_FOR_DELIVERY;
            case OUT_FOR_DELIVERY -> newStatus == OrderStatus.DELIVERED;
            default -> false;
        };

        if (!valid) {
            throw new RuntimeException("Invalid status transition");
        }

        // ✅ Update status
        order.setStatus(newStatus);

        // ✅ Set delivered time
        if (newStatus == OrderStatus.DELIVERED) {
            order.setDeliveredAt(LocalDateTime.now());
        }

        orderRepository.save(order);
    }
}