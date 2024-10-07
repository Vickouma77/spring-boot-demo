package com.upload.file_upload.controllers

import com.upload.file_upload.service.S3Service
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@Validated
class S3Controller(private val s3: S3Service) {

    companion object {
        const val UPLOAD_FILE = "/uploadFile"
    }

    @PostMapping(UPLOAD_FILE)
    suspend fun uploadFile() {
        s3.upload(
            "bucket",
            "filename",
            "folder",
            "file"
        )
    }
}