package com.prestone.datadog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DatadogApplication

fun main(args: Array<String>) {
	runApplication<DatadogApplication>(*args)
}
