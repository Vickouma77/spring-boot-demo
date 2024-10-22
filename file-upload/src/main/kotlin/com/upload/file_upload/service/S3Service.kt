package com.upload.file_upload.service

import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.buffer.DataBufferUtils
import org.springframework.http.codec.multipart.FilePart
import org.springframework.stereotype.Service
import java.io.ByteArrayInputStream
import java.io.IOException

@Service
class S3Service(private val s3: AmazonS3) {

    @Value("\${minio.buckets.kyc-bucket-name}")
    private lateinit var portfoliosBucket: String

    /**
     * Uploads a file to the specified S3 bucket.
     *
     * @param bucket the name of the S3 bucket to upload the file to.
     * @param fileName the name of the file to upload.
     * @param folder the folder in the S3 bucket to upload the file to.
     * @param part the file to upload.
     * @return a success message.
     */
    suspend fun upload(bucket: String, fileName: String, folder: String, part: FilePart): String {
        return withContext(Dispatchers.IO) {

            // Combine all parts of FilePart's content into a ByteArray
            val dataBuffer = DataBufferUtils.join(part.content()).awaitSingleOrNull()
                ?: throw IOException("Failed to read file content from multipart data.")

            // Read the content of the DataBuffer into a ByteArray
            val bytes = ByteArray(dataBuffer.readableByteCount())
            dataBuffer.read(bytes)

            // Release the DataBuffer to free up resources
            DataBufferUtils.release(dataBuffer)

            // Set metadata
            val metadata = ObjectMetadata().apply {
                contentLength = bytes.size.toLong()
                contentType = part.headers().contentType.toString()
                contentEncoding = part.headers().contentDisposition.type
            }

            val inputStream = ByteArrayInputStream(bytes)

            log.info("Uploading to S3 bucket :: $bucket, file path: $folder/$fileName")

            // Upload file to S3
            val result = s3.putObject(PutObjectRequest(bucket, "$folder/$fileName", inputStream, metadata))

            // Optionally log or use the PutObjectResult
            log.info("File upload result: ${result.eTag}")

            // Return a success message
            "File uploaded successfully: $fileName"
        }
    }

    /**
     * Deletes a file from the specified S3 bucket.
     *
     * @param bucket the name of the S3 bucket.
     * @param fileName the name of the file to delete.
     * @param folder the folder where the file resides in the S3 bucket.
     */
    suspend fun delete(bucket: String, fileName: String, folder: String, part: FilePart) {
        withContext(Dispatchers.IO) {
            log.info("Deleting from S3 bucket :: $bucket, file path: $folder/$fileName")
            s3.deleteObject(bucket, "$folder/$fileName")
        }
    }

    companion object {
        var log: Logger = LoggerFactory.getLogger(S3Service::class.java)
    }
}
