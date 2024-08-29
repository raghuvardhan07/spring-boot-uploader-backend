package com.raghu.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import com.raghu.cloudstorage.models.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username); // Correct method

    Optional<UserEntity> findById(Long id); // Correct method if ID is Long

}
