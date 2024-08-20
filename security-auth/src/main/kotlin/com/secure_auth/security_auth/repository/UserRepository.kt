package com.secure_auth.security_auth.repository

import com.secure_auth.security_auth.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
public interface UserRepository: JpaRepository<Users, Long> {

    fun findByUsername(username: String): Optional<Users>

    fun existsByEmail(email: String): Boolean

    fun findByUsernameOrEmail(username: String, email: String): Optional<Users>

    fun existsByUsername(username: String): Boolean
}