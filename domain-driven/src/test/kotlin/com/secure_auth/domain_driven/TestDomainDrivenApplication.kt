package com.secure_auth.domain_driven

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<DomainDrivenApplication>().with(TestcontainersConfiguration::class).run(*args)
}
