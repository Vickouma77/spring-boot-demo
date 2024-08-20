package com.secure_auth.security_auth.repository

import com.secure_auth.security_auth.entity.Role
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
public interface RoleRepository: JpaRepository<Role, Long> {

    fun findByName(name: String): Optional<Role>
}