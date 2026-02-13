package org.fastokart.mapper;

import org.fastokart.dto.CategoryRequest;
import org.fastokart.dto.CategoryResponse;
import org.fastokart.model.CategoryModel;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    // Convert CategoryRequest → CategoryModel
    public CategoryModel toEntity(CategoryRequest request) {
        if (request == null) return null;

        CategoryModel model = new CategoryModel();
        model.setName(request.getName());
        model.setIsActive(request.getActive() != null ? request.getActive() : true); // default true
        // ❌ Do NOT set image here, it will be handled separately in service
        return model;
    }

    // Convert CategoryModel → CategoryResponse
    public CategoryResponse toResponse(CategoryModel model) {
        if (model == null) return null;

        CategoryResponse response = new CategoryResponse();
        response.setId(model.getId());
        response.setName(model.getName());
        response.setActive(model.getIsActive());
        response.setImageName(model.getImageName()); // just the file name
        return response;
    }
}
