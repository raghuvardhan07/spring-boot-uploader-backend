package com.raghu.cloudstorage.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class File {
    @Id
    private String fileId;

    private String fileName;

    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "id")
    @JsonIgnore
    private UserEntity user;

    public Long getUserId() {
        return user != null ? user.getId() : null;
    }
}