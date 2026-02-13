package org.fastokart.Controller.api;

import org.fastokart.dto.SubCategoryRequest;
import org.fastokart.dto.SubCategoryResponse;
import org.fastokart.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
}
