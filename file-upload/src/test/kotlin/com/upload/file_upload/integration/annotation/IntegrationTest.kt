package com.upload.file_upload.integration.annotation

import com.upload.file_upload.integration.initializer.TestContainersInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@ContextConfiguration(initializers = [TestContainersInitializer::class])
@ActiveProfiles("test")
annotation class IntegrationTest
