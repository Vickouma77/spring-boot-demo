package com.upload.file_upload

import com.upload.file_upload.integration.initializer.TestContainersInitializer
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestContainersInitializer::class)
@SpringBootTest
class FileUploadApplicationTests {

	@Test
	fun contextLoads() {
	}

}
