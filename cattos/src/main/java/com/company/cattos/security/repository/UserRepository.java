package com.company.cattos.security.repository;

import com.company.cattos.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Integer> {

    // finding the user by email (email = username)
    Optional<User> findUserByEmail(String email);
    boolean existsByEmail(String email);
    User findUserByUuid(UUID uuid);

}
