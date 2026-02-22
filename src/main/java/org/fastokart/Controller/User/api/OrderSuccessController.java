package org.fastokart.Controller.User.api;

import org.fastokart.model.OrderModel;
import org.fastokart.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class OrderSuccessController {
    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/order-success/{id}")
    public String orderSuccess(@PathVariable Long id, Model model) {

        OrderModel order = orderRepository.findById(id).orElse(null);

        if(order == null){
            return "redirect:/";
        }

        model.addAttribute("order", order);
        return "user/order_success";
    }
}
