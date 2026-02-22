package org.fastokart.Controller.User;

import jakarta.servlet.http.HttpSession;
import org.fastokart.dto.BuyNowItem;
import org.fastokart.model.Cart;
import org.fastokart.model.CartItem;
import org.fastokart.model.ProductModel;
import org.fastokart.repository.ProductRepository;
import org.fastokart.service.CartService;
import org.fastokart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class UserPageController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
         private CartService cartService;
    @Autowired
    private ProductService productService;
    @GetMapping("/cart")

    public String viewCartPage() {


        return "user/cart";
    }



    @GetMapping("/product/{id}")
    public String productDetails(@PathVariable Long id, Model model) {
        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        model.addAttribute("product", product);
        return "user/product"; // Thymeleaf template: src/main/resources/templates/user/product.html
    }

    @GetMapping("/search-result")
    public String searchResult(@RequestParam("q") String query, Model model) {
        System.out.println("Keyword: " + query);   // 🔥 DEBUG 1

        List<ProductModel> products = productService.searchProducts(query);

        System.out.println("Products size: " + products.size());  // 🔥 DEBUG 2
        System.out.println("Products list: " + products);         // 🔥 DEBUG 3

        model.addAttribute("products", products);
        model.addAttribute("keyword", query);

        return "user/search_result";
    }
    @GetMapping("/checkout")
    public String checkout(HttpSession session, Model model){

        BuyNowItem item = (BuyNowItem) session.getAttribute("BUY_NOW");

        if(item == null){
            return "redirect:/";
        }

        // Always fetch product from DB
        ProductModel product = productService.getProductById(item.getProductId());

        double total = product.getPrice() * item.getQuantity();

        model.addAttribute("product", product);
        model.addAttribute("qty", item.getQuantity());
        model.addAttribute("total", total);

        return "user/checkout";
    }
}
