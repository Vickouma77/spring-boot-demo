package com.upload.file_upload

import com.upload.file_upload.integration.initializer.TestContainersInitializer
import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<FileUploadApplication>().with(TestContainersInitializer::class).run(*args)
}
