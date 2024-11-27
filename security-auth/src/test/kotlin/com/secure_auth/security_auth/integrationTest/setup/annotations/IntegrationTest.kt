package com.secure_auth.security_auth.integrationTest.setup.annotations

import com.secure_auth.security_auth.integrationTest.setup.initializer.TestContainerInitializer
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest
@ContextConfiguration(initializers = [TestContainerInitializer::class])
@ActiveProfiles("test")
annotation class IntegrationTest()
