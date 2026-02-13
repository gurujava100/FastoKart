package org.fastokart.Controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/products")
public class ProductPageController {
    @GetMapping("/")
    public String page() {
        return "admin/product";
    }
}
