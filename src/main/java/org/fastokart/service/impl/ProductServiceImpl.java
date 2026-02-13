package org.fastokart.service.impl;

import jakarta.transaction.Transactional;
import org.fastokart.dto.ProductRequest;
import org.fastokart.dto.ProductResponse;
import org.fastokart.mapper.ProductMapper;
import org.fastokart.model.ProductModel;
import org.fastokart.repository.ProductRepository;
import org.fastokart.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Transactional
public class ProductServiceImpl   implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private static final String UPLOAD_DIR = "uploads/products";

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductResponse addProduct(ProductRequest request) {

        ProductModel product = productMapper.toEntity(request);

        saveImageIfPresent(request.getImageFile(), product);

        ProductModel savedProduct = productRepository.save(product);
        return productMapper.toResponse(savedProduct);
    }

    private void saveImageIfPresent(MultipartFile file, ProductModel product) {
        if (file != null && !file.isEmpty()) {
            try {
                String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
                Path uploadPath = Paths.get(UPLOAD_DIR);
                Files.createDirectories(uploadPath);
                Files.copy(file.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);

                product.setImageName(fileName);
            } catch (IOException e) {
                throw new RuntimeException("Failed to save product image", e);
            }
        }
    }
    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(productMapper::toResponse)
                .toList();
    }

    public ProductResponse updateProduct(Long id, ProductRequest request) {

        ProductModel product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(request.getName());
        product.setPrice(request.getPrice());

        product.setActive(request.isActive());

        ProductModel saved = productRepository.save(product);

        return productMapper.toResponse(saved);
    }

    public void deleteProduct(Long id) {

        if (!productRepository.existsById(id)) {
            throw new RuntimeException("product not found");
        }

        productRepository.deleteById(id);
    }
}
