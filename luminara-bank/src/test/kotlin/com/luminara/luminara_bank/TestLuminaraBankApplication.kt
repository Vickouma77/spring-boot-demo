package com.luminara.luminara_bank

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<LuminaraBankApplication>().with(TestcontainersConfiguration::class).run(*args)
}
