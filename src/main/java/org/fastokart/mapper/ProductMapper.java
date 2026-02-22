package org.fastokart.mapper;


import org.fastokart.dto.ProductRequest;
import org.fastokart.dto.ProductResponse;
import org.fastokart.model.ProductModel;
import org.springframework.stereotype.Component;

@Component
    public class ProductMapper {

    public ProductResponse toResponse(ProductModel product) {
        ProductResponse res = new ProductResponse();

        res.setId(product.getId());
        res.setName(product.getName());
        res.setDescription(product.getDescription());
        res.setPrice(product.getPrice());
       // res.setDiscountPercent(product.getDiscountPercent());
       // res.setDiscountPrice(product.getDiscountPrice());
        res.setStock(product.getStock());
        res.setActive(product.isActive());
        res.setImageName(product.getImageName());

        if(product.getSubCategory()!=null){
            res.setSubCategoryName(product.getSubCategory().getName());
            res.setCategoryName(product.getSubCategory().getCategory().getName());
        }

        return res;
    }

    // REQUEST -> ENTITY
    public ProductModel toEntity(ProductRequest req) {

        ProductModel product = new ProductModel();

        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
        //product.setDiscountPercent(req.getDiscountPercent());
        product.setStock(req.getStock());
        product.setActive(req.isActive());

        // ⭐ calculate selling price
      /*  double discountPrice =
                req.getPrice() - (req.getPrice() * req.getDiscountPercent() / 100);

        product.setDiscountPrice(discountPrice);*/

        return product;
    }
    // UPDATE EXISTING PRODUCT
    public void updateEntity(ProductModel product, ProductRequest req) {

        product.setName(req.getName());
        product.setDescription(req.getDescription());
        product.setPrice(req.getPrice());
       // product.setDiscountPercent(req.getDiscountPercent());
        product.setStock(req.getStock());
        product.setActive(req.isActive());

        // recalculate price
       /* double discountPrice =
                req.getPrice() - (req.getPrice() * req.getDiscountPercent() / 100);

        product.setDiscountPrice(discountPrice);*/
    }
    }

