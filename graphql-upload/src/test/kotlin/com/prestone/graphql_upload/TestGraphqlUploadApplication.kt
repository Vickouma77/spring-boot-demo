package com.prestone.graphql_upload

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<GraphqlUploadApplication>().with(TestcontainersConfiguration::class).run(*args)
}
