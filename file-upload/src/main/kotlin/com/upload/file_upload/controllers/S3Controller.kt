package com.upload.file_upload.controllers

import com.upload.file_upload.service.S3Service
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.codec.multipart.FilePart
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController


@RestController
@Validated
class S3Controller {
    private val logger = LoggerFactory.getLogger(S3Controller::class.java)

    @Autowired
    private lateinit var s3: S3Service

    companion object {
        const val UPLOAD_FILE = "/uploadFile"
    }

    @Value("\${minio.buckets.kyc-bucket-name}")
    private val portfoliosBucket: String = ""

    @PostMapping(UPLOAD_FILE, consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    suspend fun uploadFile(@RequestPart("file") file: FilePart): ResponseEntity<String> {
        logger.info("Received file: ${file.filename()} for upload")
        return try {
            val fileName = file.filename()
            s3.upload(
                bucket = portfoliosBucket,
                fileName = fileName,
                folder = "portfolios",
                part = file
            )
            logger.info("File uploaded successfully: $fileName")
            ResponseEntity.ok("File uploaded successfully: $fileName")
        } catch (e: Exception) {
            logger.error("Failed to upload file: ${e.message}", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: ${e.message}")
        }
    }

    @DeleteMapping
    suspend fun deleteFile(@RequestPart("file") file: FilePart): ResponseEntity<String> {
        logger.info("Received file: ${file.filename()} for deletion")
        return try {
            val fileName = file.filename()
            s3.delete(
                bucket = portfoliosBucket,
                fileName = fileName,
                folder = "portfolios",
                part = file
            )
            logger.info("File deleted successfully: $fileName")
            ResponseEntity.ok("File deleted successfully: $fileName")
        } catch (e: Exception) {
            logger.error("Failed to delete file: ${e.message}", e)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete file: ${e.message}")
        }
    }
}