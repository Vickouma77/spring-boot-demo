package com.upload.file_upload.integration.annotation

import com.upload.file_upload.integration.initializer.TestContainersInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

/**
 * Annotation for integration tests.
 *
 * This annotation combines several other annotations commonly used in integration tests:
 * - \@SpringBootTest: Indicates that the test should start a Spring Boot application context.
 * - \@ContextConfiguration: Specifies the initializers to use for the application context.
 * - \@ActiveProfiles: Activates the "test" profile.
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(properties = ["spring.main.web-application-type=reactive"])
@ContextConfiguration(initializers = [TestContainersInitializer::class])
@ActiveProfiles("test")
annotation class IntegrationTest
