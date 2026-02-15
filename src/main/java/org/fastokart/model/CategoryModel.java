package org.fastokart.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.util.List;

@Entity
@Table(name = "catag")
@Builder
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String imageName;

    private Boolean isActive=true;
    @OneToMany(mappedBy = "category",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<SubCategory> subCategories;
    public CategoryModel(Long id, String name, String imageName, Boolean isActive,List<SubCategory> subCategories) {
        this.id = id;
        this.name = name;
        this.imageName = imageName;
        this.isActive = isActive;
        this.subCategories = subCategories;
    }
    public CategoryModel(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

      public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public List<SubCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<SubCategory> subCategories) {
        this.subCategories = subCategories;
    }

}
