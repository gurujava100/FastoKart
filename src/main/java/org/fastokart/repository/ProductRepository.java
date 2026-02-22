package org.fastokart.repository;
import org.fastokart.model.ProductModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductModel, Long> {


    // product search
    List<ProductModel> findByNameContainingIgnoreCaseOrderByNameAsc(String query);

    // suggestions dropdown
    List<ProductModel> findTop5ByNameContainingIgnoreCaseOrderByNameAsc(String keyword);
    ///@Query("SELECT p.name FROM ProductModel p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%')) ORDER BY p.name ASC")
   // List<String> findTop5Names(@Param("keyword") String keyword, Pageable pageable);
}


