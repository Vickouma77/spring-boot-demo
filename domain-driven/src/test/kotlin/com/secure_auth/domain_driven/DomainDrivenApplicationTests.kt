package com.secure_auth.domain_driven

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class DomainDrivenApplicationTests {

	@Test
	fun contextLoads() {
	}

}
