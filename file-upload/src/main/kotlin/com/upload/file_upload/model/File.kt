package com.upload.file_upload.model

import jakarta.persistence.*
import java.util.UUID

@Entity
@Table(name = "files")
class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L

    val fileName: String = ""

    val fileType: String = ""

    val fileSize: Long = 0L

    val fileDownloadUri: String = ""
}