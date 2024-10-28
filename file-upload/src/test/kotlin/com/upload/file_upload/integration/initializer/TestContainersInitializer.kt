package com.upload.file_upload.integration.initializer


import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.utility.DockerImageName
import java.time.Duration

class TestContainersInitializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
	override fun initialize(applicationContext: ConfigurableApplicationContext) {

		runBlocking {
			val postgresDeferred = async(IO) { createPostgresContainer() }
			val postgres = postgresDeferred.await()

			// Asynchronously create and start the MinIO container
			val minioDeferred = async(IO) { createMinioContainer() }

			// Wait for the MinIO container to be ready
			val minio = minioDeferred.await()

			// Add properties to the Spring application context environment
			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
				applicationContext,
				"POSTGRES_JDBC_URL=${postgres.jdbcUrl}",
				"POSTGRES_USERNAME=${postgres.username}",
				"POSTGRES_PASSWORD=${postgres.password}",
				"MINIO_URL=http://${minio.host}:${minio.getMappedPort(9000)}",
				"MINIO_ACCESS_KEY=minio",
				"MINIO_SECRET_KEY=minio123"
			)
		}
	}

	private fun createPostgresContainer(): PostgreSQLContainer<Nothing> {
		return PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:13-alpine")).apply {
			withDatabaseName("file_upload")
			withUsername("postgres")
			withPassword("root")
			waitingFor(Wait.forLogMessage(".*database system is ready to accept connections.*", 1)
				.withStartupTimeout(Duration.ofMinutes(5)))

			start()  // Start the container right after configuration
		}
	}

	private fun createMinioContainer(): GenericContainer<*> {
		return GenericContainer<Nothing>("bitnami/minio:2024.10.2").apply {
			withExposedPorts(9000)
			withEnv("MINIO_ROOT_USER", "minio")
			withEnv("MINIO_ROOT_PASSWORD", "minio123")
			waitingFor(Wait.forLogMessage(".*API:.*localhost.*", 1)
				.withStartupTimeout(Duration.ofMinutes(5)))

			start()  // Start the container right after configuration
		}
	}

}

