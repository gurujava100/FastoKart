package org.fastokart.service.impl;

import org.fastokart.dto.BuyNowItem;
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