package org.fastokart.Controller;

import org.fastokart.dto.ProductResponse;
import org.fastokart.model.CategoryModel;
import org.fastokart.model.ProductModel;
import org.fastokart.service.CategoryService;
import org.fastokart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
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
