package com.upload.file_upload

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestcontainersConfiguration::class)
@SpringBootTest
class FileUploadApplicationTests {

	@Test
	fun contextLoads() {
	}

}
