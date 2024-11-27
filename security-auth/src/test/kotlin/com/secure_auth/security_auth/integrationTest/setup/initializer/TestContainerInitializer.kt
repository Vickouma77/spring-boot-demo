package com.secure_auth.security_auth.integrationTest.setup.initializer


import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

class TestContainerInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {

    override fun initialize(applicationContext: ConfigurableApplicationContext) {

            TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
                applicationContext,
                "spring.datasource.url=${createPostgresContainer().jdbcUrl}",
                "spring.datasource.username=test",
                "spring.datasource.password=test"
            )
    }

    private fun createPostgresContainer(): PostgreSQLContainer<Nothing> {
        val imageName =
            DockerImageName.parse("postgres:13.3-alpine").asCompatibleSubstituteFor("postgres")

        val server =PostgreSQLContainer<Nothing>(imageName).apply {
            withDatabaseName("security")
            withUsername("test")
            withPassword("test")
            withReuse(true)
        }

        server.start()

        return server
    }
}