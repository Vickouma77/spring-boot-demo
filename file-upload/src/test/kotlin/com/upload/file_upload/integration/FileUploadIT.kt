package com.upload.file_upload.integration

import com.upload.file_upload.controllers.S3Controller
import com.upload.file_upload.integration.annotation.IntegrationTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters


@IntegrationTest
@AutoConfigureWebTestClient
class FileUploadIT {

    @Autowired
    lateinit var webTestClient: WebTestClient

    private val fileName = "testFile.jpg"

    @Test
    fun `should upload file successfully`() {
        val multiPartBodyBuilder = MultipartBodyBuilder()
        val resource = ClassPathResource("testFile.jpg")

        multiPartBodyBuilder
            .part("file", resource)
            .filename(fileName)
            .contentType(MediaType.MULTIPART_FORM_DATA)

        webTestClient.post()
            .uri(S3Controller.UPLOAD_FILE)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(multiPartBodyBuilder.build()))
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("File uploaded successfully: $fileName")
    }
}
