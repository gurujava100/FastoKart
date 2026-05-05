package org.fastokart.Controller.User.api;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/my-account")
public class AccountPageController {
    @GetMapping("/orders")
    public String ordersPage(HttpSession session) {

        if (session.getAttribute("USER_ID") == null) {
            return "redirect:/login"; // ✅ protect page
        }

        return "user/orders";
    }
    @GetMapping("/wishlist")
    public String wishlistPage(HttpSession session) {

        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return "redirect:/login"; // 🔥 important
        }

        return "user/wishlist"; // 👉 wishlist.html
    }
}
