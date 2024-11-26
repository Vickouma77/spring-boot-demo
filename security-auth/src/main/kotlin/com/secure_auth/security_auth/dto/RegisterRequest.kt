package com.secure_auth.security_auth.dto

data class RegisterRequest(
    val name: String,
    val username: String,
    val email: String,
    val password: String
)
