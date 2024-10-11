package com.upload.file_upload.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.services.s3.model.PutObjectResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.io.PipedInputStream
import java.io.PipedOutputStream


@Service
class S3Service @Autowired constructor(private val s3: AmazonS3) {

    /**
     *  The maximum file size allowed for uploads.
     *  The name of the S3 bucket used for KYC documents.
     *  */
//    @Value("\${minio.buckets.kyc-bucket}")
    private val kycBucket: String = ""

    /**
     * The name of the S3 bucket used for portfolios.
     */
    @Value("\${minio.buckets.kyc-bucket-name}")
    private val portfoliosBucket: String = ""

    /**
     * The maximum file size allowed for uploads.
     */
//    @Value("\${spring.servlet.multipart.max-file-size}")
    private val maxFileSize: Int = 0

    /**
     * Uploads a file to the specified S3 bucket.
     *
     * @param bucket the name of the S3 bucket to upload the file to.
     * @param fileName the name of the file to upload.
     * @param folder the folder in the S3 bucket to upload the file to.
     * @param part the file to upload.
     * @return the result of the upload operation.
     */
    suspend fun upload(bucket: String, fileName: String, folder: String, part: FilePart): PutObjectResult {
        return withContext(Dispatchers.IO) {

            val pos = PipedOutputStream()
            val pis = PipedInputStream(pos, maxFileSize)

//            DataBufferUtils.write(part, pos).awaitFirst()

            pos.close()

            DataBufferUtils.releaseConsumer()

            log.info("Uploading to S3 bucket :: {}", bucket)

            s3.putObject(PutObjectRequest(portfoliosBucket, "$folder/$fileName", pis, ObjectMetadata()))
        }
    }

    suspend fun delete(bucket: String, fileName: String, folder: String): Unit {
        return withContext(Dispatchers.IO) {
            s3.deleteObject(bucket, "$folder/$fileName")
        }
    }

    /**
     * Companion object to hold the logger instance.
     */
    companion object {
        var log: Logger = LoggerFactory.getLogger(this::class.java)
    }
}