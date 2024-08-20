package com.secure_auth.security_auth.repository

import com.secure_auth.security_auth.entity.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<Users, Long> {
}