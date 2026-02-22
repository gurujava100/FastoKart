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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class CheckoutController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @PostMapping("/place-order")
    @ResponseBody
    public Long placeOrder(
            @RequestParam String name,
            @RequestParam String phone,
            @RequestParam String address,
            @RequestParam String city,
            @RequestParam String pincode,
            @RequestParam String paymentMethod,
            HttpSession session){

        BuyNowItem item = (BuyNowItem) session.getAttribute("BUY_NOW");

        if(item == null){
            return -1L;
        }

        OrderModel order = orderService.createDirectOrder(
                item,
                name,
                phone,
                address + ", " + city + " - " + pincode,
                paymentMethod
        );

        session.removeAttribute("BUY_NOW");

        return order.getId();
    }
}
