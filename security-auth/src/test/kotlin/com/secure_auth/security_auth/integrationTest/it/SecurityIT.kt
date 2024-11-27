package com.secure_auth.security_auth.integrationTest.it

import com.secure_auth.security_auth.integrationTest.setup.annotations.IntegrationTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post


@IntegrationTest
@AutoConfigureMockMvc(addFilters = false)
class SecurityIT {

    @Autowired lateinit var mockMvc: MockMvc

    @MockitoBean private lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun setUpDatabase() {
        jdbcTemplate.execute(
            """
        CREATE TABLE IF NOT EXISTS oauth2_registered_client (
            id VARCHAR(100) NOT NULL,
            client_id VARCHAR(100) NOT NULL,
            client_id_issued_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
            client_secret VARCHAR(200) DEFAULT NULL,
            client_secret_expires_at TIMESTAMP DEFAULT NULL,
            client_name VARCHAR(200) NOT NULL,
            client_authentication_methods VARCHAR(1000) NOT NULL,
            authorization_grant_types VARCHAR(1000) NOT NULL,
            redirect_uris VARCHAR(1000) DEFAULT NULL,
            post_logout_redirect_uris VARCHAR(1000) DEFAULT NULL,
            scopes VARCHAR(1000) NOT NULL,
            client_settings VARCHAR(2000) NOT NULL,
            token_settings VARCHAR(2000) NOT NULL,
            PRIMARY KEY (id)
        )
        """.trimIndent()
        )
    }


    @Test
    fun `should allow access to public endpoints`() {
        mockMvc.get("/register")
            .andExpect {
                status { isOk() }
            }

        mockMvc.get("/welcome")
            .andExpect {
                status { isOk() }
            }

        mockMvc.get("/login")
            .andExpect {
                status { isOk() }
            }
    }
}