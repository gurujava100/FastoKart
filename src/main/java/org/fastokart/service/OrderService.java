package org.fastokart.service;

import org.fastokart.dto.BuyNowItem;
import org.fastokart.model.OrderModel;

public interface OrderService {
    OrderModel createDirectOrder(BuyNowItem item,
                                 String name,
                                 String phone,
                                 String address,
                                 String payment);
}
