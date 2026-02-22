package org.fastokart.Controller.User.api;

import jakarta.servlet.http.HttpSession;
import org.fastokart.dto.BuyNowItem;
import org.fastokart.model.OrderItemModel;
import org.fastokart.model.OrderModel;
import org.fastokart.model.ProductModel;
import org.fastokart.service.OrderService;
import org.fastokart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CheckoutController {
    @Autowired
    private OrderService orderService;
    @Autowired
    ProductService productService;
    @PostMapping("/place-order")
    @ResponseBody
    public Long placeOrder(HttpSession session){

        BuyNowItem item = (BuyNowItem) session.getAttribute("BUY_NOW");

        if(item == null){
            throw new RuntimeException("Session expired");
        }

        OrderModel order =  orderService.createDirectOrder(item,
                "Guest User",
                "9999999999",
                "Default Address",
                "COD");

        session.removeAttribute("BUY_NOW");

        return order.getId();
    }
}
