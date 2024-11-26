package com.secure_auth.security_auth.service

import com.secure_auth.security_auth.dto.RegisterRequest
import com.secure_auth.security_auth.entity.Users
import com.secure_auth.security_auth.repository.RoleRepository
import com.secure_auth.security_auth.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service


@Service
class UserService(
    private val userRepository: UserRepository,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) {

    fun registerUser(registerRequest: RegisterRequest): Users{

        if (userRepository.existsByUsername(registerRequest.username)) {
            throw Exception("Username already exists")
        }

        if (userRepository.existsByEmail(registerRequest.email)) {
            throw Exception("Email already exists")
        }

        val user = Users().apply {
            name = registerRequest.name
            username = registerRequest.username
            email = registerRequest.email
            password = passwordEncoder.encode(registerRequest.password)
        }

        val roles = roleRepository.findAll()

        roles.forEach { role ->
            user.roles.add(role)
        }

        return userRepository.save(user)
    }
}