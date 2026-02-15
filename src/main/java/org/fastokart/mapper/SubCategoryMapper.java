package org.fastokart.mapper;

import org.fastokart.dto.SubCategoryRequest;
import org.fastokart.dto.SubCategoryResponse;
import org.fastokart.model.CategoryModel;
import org.fastokart.model.SubCategory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Component
public class SubCategoryMapper {
    public SubCategoryResponse toResponse(SubCategory entity) {

        if (entity == null) return null;

        return SubCategoryResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .imageUrl("/api/subcategories/image/" + entity.getImageName())

                .isActive(entity.getActive())
                .categoryId(entity.getCategory().getId())
                .categoryName(entity.getCategory().getName())
                .build();
    }
    public SubCategory toEntity(SubCategoryRequest request, CategoryModel category) {

        if (request == null) return null;

        SubCategory subCategory = new SubCategory();
        subCategory.setName(request.getName());
        subCategory.setCategory(category);
        subCategory.setActive(true);

        return subCategory;
    }
}
