package org.fastokart.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

public class CategoryRequest {

    @NotBlank(message = "Category name is required")
    private String name;

    private Boolean active;

    private MultipartFile image; // file uploaded by user

    // Getters & Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public MultipartFile getImage() { return image; }
    public void setImage(MultipartFile image) { this.image = image; }
}
