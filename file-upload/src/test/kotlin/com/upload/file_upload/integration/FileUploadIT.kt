package com.upload.file_upload.integration

import com.amazonaws.services.s3.model.PutObjectResult
import com.upload.file_upload.integration.annotation.IntegrationTest
import com.upload.file_upload.service.S3Service
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.reactive.server.WebTestClient


@IntegrationTest
class FileUploadIT {

    @Autowired lateinit var webTestClient: WebTestClient

    @MockBean private lateinit var s3: S3Service

    @BeforeEach
    fun setup() {
        s3 = mockk(relaxed = true)
    }

    @Test
    fun `test upload to s3`() {
        val mockFile = MockMultipartFile("file", "test.jpg", "image/jpeg", "test".toByteArray())

        coEvery {
            s3.upload(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), any())
        } returns PutObjectResult()

        webTestClient.post()
            .uri("/uploadFile")
            .contentType(MediaType(MediaType.MULTIPART_FORM_DATA_VALUE))
            .bodyValue(mockFile)
            .exchange()
            .expectStatus().isOk
            .expectBody(String::class.java)
            .isEqualTo("File uploaded successfully: test.jpg")

        coVerify {
            s3.upload("kyc-bucket", "test.jpg", "portfolios", any())
        }
    }
}