package org.fastokart.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubCategoryResponse {
    private Long id;
    private String name;
    private String imageUrl;
    private Boolean isActive;

    private Long categoryId;
    private String categoryName;
}
