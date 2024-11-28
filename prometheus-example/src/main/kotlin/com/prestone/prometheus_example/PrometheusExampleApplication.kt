package com.prestone.prometheus_example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PrometheusExampleApplication

fun main(args: Array<String>) {
	runApplication<PrometheusExampleApplication>(*args)
}
