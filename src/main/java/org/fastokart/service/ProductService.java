package org.fastokart.service;

import org.fastokart.dto.ProductRequest;
import org.fastokart.dto.ProductResponse;
import org.fastokart.model.ProductModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ProductService {
    ProductResponse    addProduct(ProductRequest request);
    List<ProductResponse> getAllProducts();
    ProductResponse updateProduct(Long id, ProductRequest request);
    public void deleteProduct(Long id);

    List<ProductModel> searchProducts(String query);
    List<ProductModel> getSuggestions(String keyword);
    ProductModel getProductById(Long id);
    //List<ProductModel> getBySubcategory(Long subCategoryId);
}

