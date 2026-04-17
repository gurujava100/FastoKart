package org.fastokart.repository;

import org.fastokart.model.AddressModel;
import org.fastokart.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface AddressRepository extends JpaRepository<AddressModel, Long> {
  // List<AddressModel> findByUser(UserModel user);

    Optional<AddressModel> findByIdAndUser(Long id, UserModel user);
    List<AddressModel> findByUserId(Long userId);

    long countByUser(UserModel user);
}
