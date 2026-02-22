package org.fastokart.repository;

import org.fastokart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.cartId = :cartId")
    Optional<Cart> findByCartId(@Param("cartId") String cartId);



}
