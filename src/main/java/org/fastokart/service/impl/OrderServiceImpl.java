package org.fastokart.service.impl;

import org.fastokart.dto.BuyNowItem;
import org.fastokart.model.OrderItemModel;
import org.fastokart.model.OrderModel;
import org.fastokart.model.ProductModel;
import org.fastokart.repository.OrderRepository;
import org.fastokart.repository.ProductRepository;
import org.fastokart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public OrderModel createDirectOrder(BuyNowItem item,
                                  String name,
                                  String phone,
                                  String address,
                                  String payment){
        ProductModel product = productRepository.findById(item.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double productPrice = product.getPrice();
        int qty = item.getQuantity();
        double total = productPrice * qty;

        OrderModel order = new OrderModel();
        order.setCustomerName(name);
        order.setPhone(phone);
        order.setAddress(address);
        order.setPaymentMethod(payment);
        order.setStatus("PLACED");
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
}