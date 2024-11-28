package com.secure_auth.security_auth.config

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jose.jwk.source.ImmutableJWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.interfaces.RSAPrivateKey
import java.security.interfaces.RSAPublicKey
import java.time.Duration
import java.util.UUID


@Configuration
@EnableWebSecurity
class SpringSecurityConfig {

    /**
     * Bean definition for AuthorizationServerSettings.
     *
     * @return an instance of AuthorizationServerSettings with default settings.
     */
    @Bean
    fun authorizationServerSetting(): AuthorizationServerSettings =
        AuthorizationServerSettings.builder().build()

    /**
     * Configures the security filter chain for the authorization server.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http)

        http.getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
            .oidc(Customizer.withDefaults())

        http.exceptionHandling {
            it.accessDeniedPage("/access-denied")
        }

        return http.build()
    }

    /**
     * Configures the default security filter chain for the application.
     *
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     */
     @Bean
     @Order(Ordered.LOWEST_PRECEDENCE)
     fun defaultSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {

         http.csrf { it.disable() }
             .authorizeHttpRequests {
                 it.requestMatchers("/register/**", "/welcome/**", "/error", "/login").permitAll()
                     .requestMatchers("/oauth2/**", "/.well-known").denyAll()
                 .anyRequest().authenticated()
             }
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

    /**
     * Bean definition for RegisteredClientRepository.
     *
     * @param jdbcTemplate the JdbcTemplate to use for database operations
     * @return an instance of RegisteredClientRepository
     */
    @Bean
    fun registeredClientRepository(jdbcTemplate: JdbcTemplate): RegisteredClientRepository {
        val baseClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("client-id")
            .clientSecret(passwordEncoder().encode("client-secret"))
            .clientAuthenticationMethods { authMethods: MutableSet<ClientAuthenticationMethod> ->
                authMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                authMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            }
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .scope("client.create")
            .scope("client.read")
            .scope("client.edit")
            .scope("client.delete")
            .clientSettings(
                ClientSettings.builder()
                    .requireAuthorizationConsent(true)
                    .requireProofKey(false)
                    .build()
            )
            .tokenSettings(
                TokenSettings.builder()
                    .accessTokenFormat(OAuth2TokenFormat.SELF_CONTAINED)
                    .idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
                    .accessTokenTimeToLive(Duration.ofMinutes(30))
                    .refreshTokenTimeToLive(Duration.ofDays(1))
                    .build()
            )
            .build()

        val resourceServerClient = RegisteredClient.withId(UUID.randomUUID().toString())
            .clientId("resource-server-client")
            .clientSecret(passwordEncoder().encode("resource-server-client-secret"))
            .clientAuthenticationMethods { authMethods: MutableSet<ClientAuthenticationMethod> ->
                authMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_POST)
                authMethods.add(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
            }
            .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
            .build()

        val registeredClientRepository = JdbcRegisteredClientRepository(jdbcTemplate)

        if (!exists(baseClient, registeredClientRepository)) {
            registeredClientRepository.save(baseClient)
        }

        if (!exists(resourceServerClient, registeredClientRepository)) {
            registeredClientRepository.save(resourceServerClient)
        }

        return registeredClientRepository
    }

    /**
     * Checks if a given client exists in the repository.
     *
     * @param client the RegisteredClient to check for existence
     * @param repository the JdbcRegisteredClientRepository to search in
     * @return true if the client exists, false otherwise
     */
    private fun exists(client: RegisteredClient, repository: JdbcRegisteredClientRepository): Boolean =
        repository.findByClientId(client.clientId) != null

    /**
     * Bean definition for PasswordEncoder.
     *
     * @return an instance of BCryptPasswordEncoder
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    /**
     * Bean definition for OAuth2AuthorizationService.
     *
     * @param jdbcTemplate the JdbcTemplate to use for database operations
     * @param registeredClientRepository the repository for registered clients
     * @return an instance of OAuth2AuthorizationService
     */
    @Bean
    fun authorizationService(
        jdbcTemplate: JdbcTemplate,
        registeredClientRepository: RegisteredClientRepository
    ): OAuth2AuthorizationService =
        JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository)

    /**
     * Bean definition for OAuth2AuthorizationConsentService.
     *
     * @param jdbcTemplate the JdbcTemplate to use for database operations
     * @param registeredClientRepository the repository for registered clients
     * @return an instance of OAuth2AuthorizationConsentService
     */
    @Bean
    fun authorizationConsentService(
        jdbcTemplate: JdbcTemplate,
        registeredClientRepository: RegisteredClientRepository
    ): OAuth2AuthorizationConsentService =
        JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository)

    /**
     * Bean definition for JWKSource.
     *
     * This bean generates an RSA key pair and creates a JWKSource from it.
     *
     * @return an instance of JWKSource with the generated RSA key pair.
     */
    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val keyPair = generateRsaKey()
        val publicKey = keyPair.public as RSAPublicKey
        val privateKey = keyPair.private as RSAPrivateKey
        val rsaKey: RSAKey = RSAKey.Builder(publicKey)
            .privateKey(privateKey)
            .keyID(UUID.randomUUID().toString())
            .build()

        val jwkSet = JWKSet(rsaKey)

        return  ImmutableJWKSet<SecurityContext>(jwkSet)
    }

    /**
     * JwtDecoder for decoding signed access tokens
     */
    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder =
        OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)

    companion object {
        /**
         * KeyPair with keys generated on startup used to create the JWKSource above
         */
        private fun generateRsaKey(): KeyPair =
            try {
                val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
                keyPairGenerator.initialize(2048)
                keyPairGenerator.generateKeyPair()
            } catch (ex: Exception) { throw IllegalStateException(ex) }
    }
}