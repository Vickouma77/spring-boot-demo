package com.upload.file_upload.unit

import com.amazonaws.services.s3.model.PutObjectResult
import com.upload.file_upload.controllers.S3Controller
import com.upload.file_upload.service.S3Service
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.Import
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.http.MediaType
import org.springframework.http.codec.multipart.FilePart
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.core.publisher.Flux

@ExtendWith(SpringExtension::class)
@WebFluxTest(S3Controller::class)
@Import(S3Service::class)
class FileUploadUT {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @MockBean
    private lateinit var s3Service: S3Service

    @Value("\${minio.buckets.kyc-bucket-name}")
    private lateinit var portfoliosBucket: String

    @BeforeEach
    fun setUp() {
        // No need to reassign webTestClient or s3Service here
    }

    @Test
    fun `test upload file successfully`() {
        // Given
        val mockFilePart = mockFilePart()

        // Mock the upload method of the S3Service
       coEvery {
           s3Service.upload(portfoliosBucket, "testFile.txt", "portfolio", mockFilePart)
       } returns mockk<PutObjectResult>(relaxed = true).toString()

        // When
        webTestClient.post()
            .uri("/uploadFile")
            .contentType(MediaType.MULTIPART_FORM_DATA)
            .body(BodyInserters.fromMultipartData("file", mockFilePart))
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("File uploaded successfully: testFile.txt")
    }

    private fun mockFilePart(): FilePart {
        val mockFilePart = mockk<FilePart>()

        // Mock the filename
        every { mockFilePart.filename() } returns "testFile.txt"

        // Mocking the content of the file to return a Flux of DataBuffer
        val mockDataBuffer = mockk<DataBuffer>(relaxed = true)
        every { mockFilePart.content() } returns Flux.just(mockDataBuffer)

        return mockFilePart
    }
}
