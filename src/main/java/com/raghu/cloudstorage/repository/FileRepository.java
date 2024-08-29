package com.raghu.cloudstorage.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.raghu.cloudstorage.models.File;
import com.raghu.cloudstorage.models.UserEntity;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<File, String> {
    List<File> findByUserOrderByCreatedAtDesc(UserEntity user);

    @Query("SELECT f FROM File f WHERE f.user.id = :userId AND LOWER(f.fileName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) ORDER BY f.createdAt DESC")
    List<File> searchFilesByFileName(@Param("userId") Long userId,
            @Param("searchTerm") String searchTerm);
}
