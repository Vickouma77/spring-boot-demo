package com.secure_auth.auth_service.repository;

import com.secure_auth.auth_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByUserNameOrByEmail(String username, String email);

    boolean existsByUserName(String username);
}
