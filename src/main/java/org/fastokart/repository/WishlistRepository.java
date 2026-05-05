package org.fastokart.repository;

import org.fastokart.model.WishlistModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistModel, Long> {
    boolean existsByUserIdAndProductId(Long userId, Long productId);

    Optional<WishlistModel> findByUserIdAndProductId(Long userId, Long productId);

    List<WishlistModel> findByUserId(Long userId);
    Long countByUserId(Long userId);
}
