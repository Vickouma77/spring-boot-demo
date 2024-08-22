package com.secure_auth.security_auth.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@Controller
class WelcomeController {

    @GetMapping("/welcome")
    fun welcome(): String {
        return "welcome"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
}