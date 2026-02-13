package org.fastokart.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class SubCategoryRequest {
    private String name;
    private Long categoryId;
    private MultipartFile image;
}
