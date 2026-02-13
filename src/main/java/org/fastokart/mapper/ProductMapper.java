package org.fastokart.mapper;


import org.fastokart.dto.ProductRequest;
import org.fastokart.dto.ProductResponse;
import org.fastokart.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
    public class ProductMapper {

        // Convert Product entity to ProductResponse DTO
        public ProductResponse toResponse(ProductModel product) {
            if (product == null) return null;

            ProductResponse response = new ProductResponse();
            response.setId(product.getId());
            response.setName(product.getName());
            response.setDescription(product.getDescription());
            response.setPrice(product.getPrice());
            response.setActive(product.isActive());
           response.setImageName(product.getImageName());
            return response;
        }

        // Convert ProductRequest DTO to Product entity
        public ProductModel toEntity(ProductRequest request) {
            if (request == null) return null;

            ProductModel product =new ProductModel();
            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setActive(request.isActive());
                              //product.setImageName(request.getImageFile());
            return product;
        }

        // Update existing Product entity from ProductRequest
        public void updateEntity(ProductModel product, ProductRequest request) {
            if (product == null || request == null) return;

            product.setName(request.getName());
            product.setDescription(request.getDescription());
            product.setPrice(request.getPrice());
            product.setActive(request.isActive());
           // product.setImageName(request.getImageFile());
        }
    }

