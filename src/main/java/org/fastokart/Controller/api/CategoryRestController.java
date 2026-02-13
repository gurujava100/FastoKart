package org.fastokart.Controller.api;

import org.fastokart.dto.CategoryRequest;
import org.fastokart.dto.CategoryResponse;
import org.fastokart.model.CategoryModel;
import org.fastokart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {

    @Autowired
    private CategoryService categoryService;
    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryResponse> saveCategory(
            @ModelAttribute CategoryRequest request,
            @RequestParam(value = "image", required = false) MultipartFile image
    ) {
        CategoryResponse response = categoryService.saveCategory(request, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    // ✅ GET ALL
    @GetMapping
    public ResponseEntity<List<CategoryModel>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategory());
    }

    // ✅ GET BY ID
    @GetMapping("/{id}")
    public CategoryModel getById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    // ✅ DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.ok().build();
    }

    // ✅ UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<CategoryModel> updateCategory(
            @PathVariable Long id,
            @RequestBody CategoryModel category) {

        CategoryModel updated = categoryService.updateCategory(id, category);
        return ResponseEntity.ok(updated);
    }

    // 🔥🔥🔥 IMAGE SERVING FIX (THIS SOLVES YOUR ISSUE)
    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getCategoryImage(@PathVariable String fileName) {

        try {
            Path filePath = Paths.get("uploads/categories").resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}