package com.upload.file_upload.controllers

import com.upload.file_upload.service.S3Service
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.codec.multipart.FilePart
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController


@RestController
@Validated
class S3Controller() {

    @Autowired
    private lateinit var s3: S3Service

    companion object {
        const val UPLOAD_FILE = "/uploadFile"
    }

    @Value("\${minio.buckets.kyc-bucket-name}")
    private val portfoliosBucket: String = ""

    @PostMapping(value = [UPLOAD_FILE], consumes = ["multipart/form-data", "image/jpeg", "text/plain"])
    suspend fun uploadFile(@RequestPart("file") file: FilePart) {
        val fileName = file.filename()

        s3.upload(
            bucket = portfoliosBucket,
            fileName = fileName,
            folder = "portfolios",
            part = file
        )
    }

}