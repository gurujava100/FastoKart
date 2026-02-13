package org.fastokart.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductRequest {

    private String name;
    private String description;
    private double price;
    private boolean active ;
    private MultipartFile imageFile;

    // -------- GETTERS & SETTERS --------

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;   // ✅ correct
    }

    public void setActive(boolean active) {
        this.active = active;   // ✅ correct
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
