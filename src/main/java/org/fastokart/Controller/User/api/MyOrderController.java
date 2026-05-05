package org.fastokart.Controller.User.api;

import jakarta.servlet.http.HttpSession;
import org.fastokart.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class MyOrderController {
    @Autowired
    private OrderService orderService;
    @GetMapping("/my-orders")
    public ResponseEntity<?> getMyOrders(HttpSession session) {


        System.out.println("Orders Session: " + session.getId());

        Long userId = (Long) session.getAttribute("USER_ID");

        System.out.println("USER_ID: " + userId);

        if (userId == null) {
            return ResponseEntity.status(401)
                    .body("User not logged in");
        }

        return ResponseEntity.ok(orderService.getOrders(userId));
    }
}
