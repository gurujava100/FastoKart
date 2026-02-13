package org.fastokart.service;

import org.fastokart.dto.SubCategoryRequest;
import org.fastokart.dto.SubCategoryResponse;
import org.fastokart.model.CategoryModel;
import org.fastokart.model.SubCategory;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface SubCategoryService {
    SubCategoryResponse save(SubCategoryRequest request);
    public List<SubCategory> getAllSubCategory();
}
