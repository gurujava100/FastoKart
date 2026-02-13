package org.fastokart.Controller.admin;

import org.fastokart.model.CategoryModel;
import org.fastokart.model.SubCategory;
import org.fastokart.service.CategoryService;
import org.fastokart.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/products")
public class ProductPageController {
    @Autowired
    private SubCategoryService subCategoryService;
    @GetMapping("/")
    public String page(Model model)
    {
        List<SubCategory> categories = subCategoryService.getAllSubCategory();
        model.addAttribute("categories", categories);

        return "admin/product";
    }
}
