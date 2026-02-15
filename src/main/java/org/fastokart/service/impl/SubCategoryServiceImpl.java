package org.fastokart.service.impl;

import org.fastokart.dto.SubCategoryRequest;
import org.fastokart.dto.SubCategoryResponse;
import org.fastokart.mapper.SubCategoryMapper;
import org.fastokart.model.CategoryModel;
import org.fastokart.model.SubCategory;
import org.fastokart.repository.CategoryRepository;
import org.fastokart.repository.SubCategoryRepository;
import org.fastokart.service.SubCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class SubCategoryServiceImpl implements SubCategoryService {

     @Autowired
    private  SubCategoryRepository subCategoryRepository;
    @Autowired
    private  CategoryRepository categoryRepository;
    @Autowired
    private  SubCategoryMapper mapper;
    private static final String UPLOAD_DIR = "uploads/subcategories/";
    @Override
    public SubCategoryResponse save(SubCategoryRequest request) {

        CategoryModel category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Category Not Found"));

        SubCategory subCategory = mapper.toEntity(request, category);

        MultipartFile image = request.getImage();

        if (image != null && !image.isEmpty()) {
            try {

                Path uploadPath = Paths.get("uploads/subcategories/");
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }

                String fileName = System.currentTimeMillis() + "_"
                        + image.getOriginalFilename();

                Files.copy(image.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);

                subCategory.setImageName(fileName);

            } catch (IOException e) {
                throw new ResponseStatusException(
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "Image Upload Failed");
            }
        }

        SubCategory saved = subCategoryRepository.save(subCategory);

        return mapper.toResponse(saved);
    }

    @Override
    public List<SubCategoryResponse> getAllSubCategory() {

           return subCategoryRepository.findAll()
                .stream()
                   .map(mapper::toResponse)
                .toList();
    }

}
