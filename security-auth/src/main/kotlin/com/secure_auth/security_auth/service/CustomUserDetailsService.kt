package com.secure_auth.security_auth.service

import com.secure_auth.security_auth.entity.Users
import com.secure_auth.security_auth.repository.UserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository,
): UserDetailsService {

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(usernameOrEmail: String): UserDetails {
        val user: Users = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
            .orElseThrow { UsernameNotFoundException("User does not exists by Username or Email") }

        val authorities = user.roles.map { SimpleGrantedAuthority(it.name) }.toSet()

        return User(
            usernameOrEmail,
            user.password,
            authorities
        )
    }

}