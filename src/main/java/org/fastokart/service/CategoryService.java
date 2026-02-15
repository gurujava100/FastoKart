package org.fastokart.service;

import org.fastokart.dto.CategoryRequest;
import org.fastokart.dto.CategoryResponse;
import org.fastokart.model.CategoryModel;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CategoryService {

    CategoryResponse saveCategory(CategoryRequest request,MultipartFile image);
            public List<CategoryModel> getActiveCategories();
    List<CategoryResponse> getAllCategories();
    public void deleteCategory(Long id);
    CategoryModel updateCategory(Long id, CategoryModel category);
    public CategoryModel getCategoryById(Long id);
    //List<CategoryResponse> getAllCategories();
    /*public Boolean existCategory(String name);





    */



    //public Page<CategoryModel> getAllCategorPagination(Integer pageNo, Integer pageSize);
}
