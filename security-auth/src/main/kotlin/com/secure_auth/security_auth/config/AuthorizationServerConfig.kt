package com.secure_auth.security_auth.config
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.jdbc.core.JdbcTemplate
//import org.springframework.security.oauth2.core.AuthorizationGrantType
//import org.springframework.security.oauth2.core.oidc.OidcScopes
//import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
//import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
//import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
//import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
//import java.time.Duration
//import java.util.UUID
//
//@Configuration
//class AuthorizationServerConfig {
//
//    @Bean
//    fun registeredClientRepository(jdbcTemplate: JdbcTemplate): JdbcRegisteredClientRepository {
//        val repository = JdbcRegisteredClientRepository(jdbcTemplate)
//
//        val objectMapper = ObjectMapper().apply {
//            registerModules(JavaTimeModule())
//        }
//
//        // Define the OIDC client
//        val oidcClient = RegisteredClient.withId(UUID.randomUUID().toString())
//            .clientId("client-id")
//            .clientSecret("{bcrypt}encoded-secret")
//            .redirectUri("http://localhost:8080/login/oauth2/code/oidc")
//            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
//            .scope(OidcScopes.OPENID)
//            .scope("profile")
//            .scope("email")
//            .clientSettings(
//                ClientSettings.builder()
//                    .requireAuthorizationConsent(true)
//                    .build()
//            )
//            .tokenSettings(
//                TokenSettings.builder()
//                    .accessTokenTimeToLive(Duration.ofMinutes(30))
//                    .refreshTokenTimeToLive(Duration.ofDays(1))
//                    .build()
//            )
//            .build()
//
//        // Save the registered client if it does not already exist
//        if (repository.findByClientId("client-id") == null) {
//            repository.save(oidcClient)
//        }
//
//        return repository
//    }
//
//    @Bean
//    fun authorizationServerSettings(): AuthorizationServerSettings {
//        return AuthorizationServerSettings.builder()
//            .issuer("http://localhost:9000")
//            .build()
//    }
//}
