package org.fastokart.repository;

import jakarta.transaction.Transactional;
import org.fastokart.model.AddressModel;
import org.fastokart.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {
  // List<AddressModel> findByUser(UserModel user);
  List<AddressModel> findByUserId(Long userId);

    // Count addresses
    long countByUser(UserModel user);

    // Reset default addresses
    @Modifying
    @Transactional
    @Query("UPDATE AddressModel a SET a.isDefault = false WHERE a.user.id = :userId")
    void resetDefaultAddresses(@Param("userId") Long userId);
}
