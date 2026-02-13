package org.fastokart.repository;

import org.fastokart.model.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long> {
    boolean existsByName(String name);

    boolean existsByNameIgnoreCase(String name);
    List<CategoryModel> findByIsActiveTrue();
}
