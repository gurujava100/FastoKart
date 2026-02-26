package org.fastokart.repository;

import org.fastokart.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserModel,Long> {
    // check duplicate email (used in register)
    boolean existsByEmail(String email);
}
