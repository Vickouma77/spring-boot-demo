package com.secure_auth.security_auth.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
class SpringSecurityConfig(
    private val userDetailsService: UserDetailsService
)
 {
    @Bean
    fun passwordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder(10)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http.csrf { it.disable() }
            .authorizeHttpRequests { it.anyRequest().authenticated() }
            .formLogin {
                it.loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/welcome")
                    .permitAll()
            }
            .logout {
                it.logoutRequestMatcher(AntPathRequestMatcher("/logout"))
                    .permitAll()
            }
        return http.build()
    }

    @Bean
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager{
        return configuration.authenticationManager
    }
}


