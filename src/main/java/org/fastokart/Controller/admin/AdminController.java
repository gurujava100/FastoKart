package org.fastokart.Controller.admin;

import org.fastokart.model.CategoryModel;
import org.fastokart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private    CategoryService categoryService;
    @GetMapping("/")
    public String Index()
    {
        return  "admin/index";
    }

    @GetMapping("/subcategories")
    public String subCategoryPage(Model model) {

          List<CategoryModel> categories = categoryService.getAllCategory();
          model.addAttribute("categories", categories);

          return "admin/sub_category";
    }
}
