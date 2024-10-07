package com.upload.file_upload

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<FileUploadApplication>().with(TestcontainersConfiguration::class).run(*args)
}
