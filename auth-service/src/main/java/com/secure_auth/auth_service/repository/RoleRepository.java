package com.secure_auth.auth_service.repository;

import com.secure_auth.auth_service.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository {
    Optional<User> findName(String name);
}
