package org.fastokart.Controller.User;

import jakarta.servlet.http.HttpSession;
import org.fastokart.dto.AddressResponseDTO;
import org.fastokart.dto.BuyNowItem;
import org.fastokart.model.*;
import org.fastokart.repository.ProductRepository;
import org.fastokart.service.AddressService;
import org.fastokart.service.CartService;
import org.fastokart.service.CategoryService;
import org.fastokart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    @Autowired
    private CategoryService categoryService;
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
    @GetMapping("/login")
    public String loginPage() {
        return "user/login";   // login.html
    }
    @GetMapping("/register")
    public String showRegisterPage() {
        return "user/register";
    }
    @GetMapping("/index")
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) Long category,
            @RequestParam(required = false) Long userId,
            Model model) {

        int productSize = 6;

        Page<ProductModel> productPage = (category != null)
                ? productService.getProductsByCategoryId(category, page, productSize)
                : productService.getAllActiveProducts(page, productSize);

        // Debug (remove later in production)
        System.out.println("Total Elements: " + productPage.getTotalElements());
        System.out.println("Total Pages: " + productPage.getTotalPages());
        System.out.println("Total Pages: " + userId);

        model.addAttribute("categories", categoryService.getAllCategories());

        // ✅ Instead of passing many attributes, pass full Page object
        model.addAttribute("productPage", productPage);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("userId", userId);
        return "user/Home";
    }

    @GetMapping("/my-account/address/add-page")
    public String addAddressPage(HttpSession session) {
       /* UserModel user = (UserModel) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }*/
        return "user/add-address"; // Thymeleaf template: add-address.html
    }

    @Autowired
    private AddressService addressService;

    @GetMapping("/account/addresses")
    public String addressesPage(Model model, HttpSession session) {
       // UserModel user = (UserModel) session.getAttribute("user");

        System.out.println("👉 ADDRESS API CALLED");
        System.out.println("Session ID (address): " + session.getId());
        System.out.println("USER_ID from session: " + session.getAttribute("USER_ID"));
        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return "redirect:/login";
        }

        List<AddressResponseDTO> addresses =
                addressService. getAllAddresses(userId);

        // Send to UI
        model.addAttribute("addresses", addresses);
        return "user/address"; // Thymeleaf template name: address.html
    }
    @GetMapping("/my-account/address/edit/{id}")
    public String editAddress(@PathVariable Long id,
                              Model model,
                              HttpSession session) {
        Long userId = (Long) session.getAttribute("USER_ID");

        if (userId == null) {
            return "redirect:/login"; // 🔐 session expired
        }

        AddressResponseDTO address = addressService.getAddressById(id, userId);

        model.addAttribute("address", address);
        return "user/edit-address";
    }
    @RequestMapping("/my-account/updateaddress")

        @GetMapping
        public String addressPage(Model model, HttpSession session) {

            Long userId = (Long) session.getAttribute("USER_ID");

            if (userId == null) {
                return "redirect:/login";
            }

            model.addAttribute("addresses",
                    addressService.getAllAddresses(userId));

            return "user/address";
        }

}
