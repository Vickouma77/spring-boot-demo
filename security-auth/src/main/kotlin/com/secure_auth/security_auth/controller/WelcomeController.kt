package com.secure_auth.security_auth.controller

import com.secure_auth.security_auth.dto.RegisterRequest
import com.secure_auth.security_auth.service.UserService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class WelcomeController(private val userService: UserService) {

    @GetMapping("/welcome")
    fun welcome(): String {
        return "welcome"
    }

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }

    @GetMapping("/register")
    fun register(model: Model): String {
        model.addAttribute("registerRequest", RegisterRequest(username = "", password = "", email = "", name = ""))
        return "register"
    }

    @PostMapping("/register")
    fun register(@ModelAttribute registerRequest: RegisterRequest, @RequestParam("confirmPassword") confirmPassword: String): String {
        if (registerRequest.password != confirmPassword) {
            return "redirect:/register?error=passwordsDoNotMatch"
        }

        userService.registerUser(registerRequest)
        return "redirect:/login"
    }

    @GetMapping("/error")
    fun error(): String {
        return "error"
    }
}
