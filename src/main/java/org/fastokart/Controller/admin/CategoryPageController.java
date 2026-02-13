package org.fastokart.Controller.admin;

import org.fastokart.dto.CategoryRequest;
import org.fastokart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categories")
public class CategoryPageController {

    private final CategoryService categoryService;

    public CategoryPageController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    public String page() {
        return "admin/category";
    }
}
