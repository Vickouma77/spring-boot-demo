package com.secure_auth.domain_driven

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DomainDrivenApplication

fun main(args: Array<String>) {
	runApplication<DomainDrivenApplication>(*args)
}
