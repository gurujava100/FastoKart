package org.fastokart.Controller.api;

import org.springframework.core.io.Resource;
import org.fastokart.dto.CategoryRequest;
import org.fastokart.dto.CategoryResponse;
import org.fastokart.dto.ProductRequest;
import org.fastokart.dto.ProductResponse;
import org.fastokart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<ProductResponse> saveProduct(
            @ModelAttribute ProductRequest request,
            @RequestParam(value = "imageFile", required = false) MultipartFile imageFile
    ) {
        request.setImageFile(imageFile);

        ProductResponse response = productService.addProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
//getting image
@GetMapping("/image/{fileName}")
public ResponseEntity<Resource> getProductImage(@PathVariable String fileName) {
    try {
        if (fileName.contains("..")) {
            return ResponseEntity.badRequest().build();
        }

        Path filePath = Paths.get("uploads/products").resolve(fileName);
             org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        String contentType = Files.probeContentType(filePath);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .body(resource);

    } catch (Exception e) {
        return ResponseEntity.notFound().build();
    }
}

@PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {

       ProductResponse updatedProduct = productService.updateProduct(id, request);
       System.out.println();
       return ResponseEntity.ok(updatedProduct);

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
               productService.deleteProduct(id);
               return ResponseEntity.ok().build();
    }
}
