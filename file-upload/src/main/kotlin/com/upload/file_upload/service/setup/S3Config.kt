package com.upload.file_upload.service.setup

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config {

    @Value("\${s3.base-url}") private var endpoint: String = ""
    @Value("\${s3.region}") private var region: String = ""
    @Value("\${s3.secret-key}") private var secretKey: String = ""
    @Value("\${s3.access-key}") private val accessKey: String = ""

    /**
     * Creates and configures an AmazonS3 client bean.
     *
     * @return an instance of AmazonS3 configured with the specified endpoint, region, and credentials.
     */
    @Bean
    fun s3(): AmazonS3 {
        return AmazonS3ClientBuilder.standard()
            .withEndpointConfiguration(EndpointConfiguration(endpoint, region))
            .withCredentials(
                AWSStaticCredentialsProvider(BasicAWSCredentials(accessKey, secretKey))
            )
            .build()
    }
}