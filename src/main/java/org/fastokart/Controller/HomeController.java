package org.fastokart.Controller;

import jakarta.servlet.http.HttpSession;
import org.fastokart.dto.ProductResponse;
import org.fastokart.model.Cart;
import org.fastokart.model.CartItem;
import org.fastokart.model.CategoryModel;
import org.fastokart.model.ProductModel;
import org.fastokart.repository.CartRepository;
import org.fastokart.service.CartService;
import org.fastokart.service.CategoryService;
import org.fastokart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartRepository cartRepository;

    @ModelAttribute("cartCount")
    public int getCartCount(HttpSession session) {
        // 1️⃣ Get or create cartId for the session
        String cartId = (String) session.getAttribute("CART_ID");
        if (cartId == null) {
            cartId = session.getId(); // unique per browser session
            session.setAttribute("CART_ID", cartId);
        }

        // 2️⃣ Fetch cart if it exists
           Optional<Cart> cartOpt = cartRepository.findByCartId(cartId);
        if (cartOpt.isPresent()) {
            Cart cart = cartOpt.get();
            // sum all quantities of items
            return cart.getItems().stream().mapToInt(CartItem::getQuantity).sum();
        }

        // 3️⃣ No cart yet → return 0
        return 0;
    }

    @GetMapping("/")
      public String Login(@RequestParam(required = false) Integer category,
                          Model model)
    {
        // 1️⃣ Load categories from DB
        List<CategoryModel> categories = categoryService.getActiveCategories();
        model.addAttribute("categories", categories);
        //Listing Products
            List<ProductResponse> products = productService.getAllProducts();

            model.addAttribute("products", products);

        return  "index";
    }

}
