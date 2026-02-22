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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OrderApiController {
    @Autowired
    private ProductService productService;

    @PostMapping("/buy-now/{productId}")
    public Map<String, Object> buyNow(@PathVariable Long productId,
                                      HttpSession session){

        ProductModel product = productService.getProductById(productId);

        if(product == null){
            return Map.of("success", false);
        }

        BuyNowItem item = new BuyNowItem();
        item.setProductId(productId);
        item.setQuantity(1);

        session.setAttribute("BUY_NOW", item);

        return Map.of("success", true);
    }
}
