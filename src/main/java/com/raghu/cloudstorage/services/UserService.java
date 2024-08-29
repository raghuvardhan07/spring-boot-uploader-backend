package com.raghu.cloudstorage.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.raghu.cloudstorage.models.UserEntity;
import com.raghu.cloudstorage.repository.UserRepository;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
}
