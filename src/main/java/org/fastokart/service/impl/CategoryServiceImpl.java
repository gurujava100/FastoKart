package org.fastokart.service.impl;

import jakarta.transaction.Transactional;
import org.fastokart.dto.CategoryRequest;
import org.fastokart.dto.CategoryResponse;
import org.fastokart.mapper.CategoryMapper;

import org.fastokart.model.CategoryModel;
import org.fastokart.repository.CategoryRepository;
import org.fastokart.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper; // MUST exist


    @Override
    public CategoryResponse saveCategory(CategoryRequest request, MultipartFile image) {

        if (categoryRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Category already exists");
        }

        CategoryModel model = categoryMapper.toEntity(request);

        // ✅ Handle file upload
        if (image != null && !image.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + image.getOriginalFilename();
            try {
                Path uploadPath = Paths.get("uploads/categories");
                Files.createDirectories(uploadPath);
                Files.copy(image.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

                model.setImageName(fileName); // only filename in DB
            } catch (IOException e) {
                throw new RuntimeException("Image upload failed", e);
            }
        }

        CategoryModel saved = categoryRepository.save(model);
        return categoryMapper.toResponse(saved);
    }


    public List<CategoryModel> getActiveCategories() {
        return categoryRepository.findByIsActiveTrue();
    }

    public List<CategoryModel>getAllCategory() {
        return categoryRepository.findAll();
    }
    @Override
    public CategoryModel getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
    }

       public void deleteCategory(Long id) {

        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }

          categoryRepository.deleteById(id);
    }
    public CategoryModel updateCategory(Long id, CategoryModel updatedData) {

        CategoryModel existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existing.setName(updatedData.getName());
        existing.setIsActive(updatedData.getIsActive());

        return categoryRepository.save(existing);
    }
    }