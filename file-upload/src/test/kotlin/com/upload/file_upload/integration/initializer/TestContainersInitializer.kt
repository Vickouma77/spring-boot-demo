package com.upload.file_upload.integration.initializer

import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.test.context.support.TestPropertySourceUtils
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestContainersInitializer: ApplicationContextInitializer<ConfigurableApplicationContext> {

	override fun initialize(applicationContext: ConfigurableApplicationContext){

		runBlocking {
			val postgresDeferred = async(IO) { createPostgresContainer() }
			val minioDeferred = async(IO) { createMinioContainer() }

			//Await all containers after they are launched
			val postgres = postgresDeferred.await()
			val minio = minioDeferred.await()

			TestPropertySourceUtils.addInlinedPropertiesToEnvironment(
				applicationContext,
				"POSTGRES_JDBC_URL=${postgres.jdbcUrl}",
				"POSTGRES_USERNAME=${postgres.username}",
				"POSTGRES_PASSWORD=${postgres.password}",
				"MINIO_URL=http://${minio.host}:${minio.firstMappedPort}"
			)
		}
	}

	private fun createPostgresContainer(): PostgreSQLContainer<Nothing> {
		val imageName = DockerImageName.parse("postgres:latest").asCompatibleSubstituteFor("postgres")

		val server = PostgreSQLContainer<Nothing>(imageName).apply {
			withDatabaseName("file_upload_db")
			withUsername("postgres")
			withPassword("postgres")
			withExposedPorts(5432)
		}

		server.start()

		return server
	}

	private fun createMinioContainer(): GenericContainer<Nothing> {
		val server = GenericContainer<Nothing>("minio/minio:latest").apply {
			withExposedPorts(9000)
			withEnv("MINIO_ACCESS_KEY", "minio")
			withEnv("MINIO_SECRET_KEY", "minio123")
		}

		server.start()

		return server
	}
}
