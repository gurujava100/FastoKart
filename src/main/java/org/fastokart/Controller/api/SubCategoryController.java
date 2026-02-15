package org.fastokart.Controller.api;

import org.fastokart.dto.SubCategoryRequest;
import org.fastokart.dto.SubCategoryResponse;
import org.fastokart.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/subcategories")
public class SubCategoryController {
    @Autowired
    private SubCategoryService subCategoryService;

    @PostMapping
    public ResponseEntity<SubCategoryResponse> create(
            @ModelAttribute SubCategoryRequest request) {

        SubCategoryResponse response =
                subCategoryService.save(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<SubCategoryResponse>> getAll() {
        return ResponseEntity.ok(subCategoryService.getAllSubCategory());
    }
    @GetMapping("/image/{fileName}")
    public ResponseEntity<Resource> getSubCategoryImage(@PathVariable String fileName) {
        try {
            Path filePath = Paths.get("uploads/subcategories").resolve(fileName);
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
