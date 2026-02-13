package org.fastokart.service;

import org.fastokart.dto.SubCategoryRequest;
import org.fastokart.dto.SubCategoryResponse;
import org.springframework.web.multipart.MultipartFile;

public interface SubCategoryService {
    SubCategoryResponse save(SubCategoryRequest request);
}
