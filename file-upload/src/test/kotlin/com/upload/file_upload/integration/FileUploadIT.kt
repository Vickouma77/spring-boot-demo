package com.upload.file_upload.integration

import com.upload.file_upload.controllers.S3Controller
import com.upload.file_upload.integration.annotation.IntegrationTest
import com.upload.file_upload.service.S3Service
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.core.io.ClassPathResource
import org.springframework.http.MediaType
import org.springframework.http.client.MultipartBodyBuilder
import org.springframework.http.codec.multipart.FilePart
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters

@IntegrationTest
@WebFluxTest(S3Controller::class)
class FileUploadIT {

    @MockBean
    private lateinit var s3Service: S3Service

    @Autowired
    lateinit var webTestClient: WebTestClient

    private val folder = "portfolios"
    private val fileName = "testFile.jpg"

    @BeforeEach
    fun setup() {
        s3Service = mockk()
    }

    @Test
    fun `should upload file successfully`() {
        val multiPartBodyBuilder = MultipartBodyBuilder()
        val resource = ClassPathResource("testFile.jpg")
        multiPartBodyBuilder.part("file", resource).filename(fileName)
            .contentType(MediaType.MULTIPART_FORM_DATA)

        val bucket = "kyc-bucket"
        coEvery { s3Service.upload(bucket, fileName, folder, any<FilePart>()) } returns "File uploaded successfully"

        webTestClient.post()
            .uri(S3Controller.UPLOAD_FILE)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(multiPartBodyBuilder.build()))
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("File uploaded successfully: $fileName")

        coVerify(exactly = 1) { s3Service.upload(bucket, fileName, folder, any<FilePart>()) }
    }

    @Test
    fun `should return internal server error when file upload fails`() {
        val multiPartBodyBuilder = MultipartBodyBuilder()
        val resource = ClassPathResource("testFile.jpg")
        multiPartBodyBuilder.part("file", resource).filename(fileName)
            .contentType(MediaType.MULTIPART_FORM_DATA)

        val bucket = "kyc-bucket"
        coEvery { s3Service.upload(bucket, fileName, folder, any<FilePart>()) } throws Exception("Upload failed")

        webTestClient.post()
            .uri(S3Controller.UPLOAD_FILE)
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData(multiPartBodyBuilder.build()))
            .exchange()
            .expectStatus().is5xxServerError
            .expectBody(String::class.java)
            .isEqualTo("Failed to upload file: Upload failed")

        coVerify(exactly = 1) { s3Service.upload(bucket, fileName, folder, any<FilePart>()) }
    }
}
