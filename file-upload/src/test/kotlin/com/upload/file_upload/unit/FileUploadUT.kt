package com.upload.file_upload.unit

import com.amazonaws.services.s3.model.PutObjectResult
import com.upload.file_upload.controllers.S3Controller
import com.upload.file_upload.service.S3Service
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.mock.web.MockMultipartFile
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [S3Controller::class])
@ExtendWith(MockitoExtension::class)
class FileUploadUT {

    @Autowired private lateinit var mockMvc: MockMvc

    @MockBean private lateinit var s3: S3Service

    @BeforeEach
    fun setUp() {
        s3 = mockk()
    }

    @Test
    fun `should upload file`() {
        val mockFile = MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "test".toByteArray())
        val bucket = "kyc-bucket"
        val folder = "portfolios"

        coEvery {
            s3.upload(bucket, any(), any(), any() )
        } returns mockk()

        mockMvc.perform(MockMvcRequestBuilders.multipart(S3Controller.UPLOAD_FILE)
            .file(mockFile)
            .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("File uploaded successfully: ${mockFile.originalFilename}"
        ))
    }
}