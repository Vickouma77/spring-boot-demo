package com.prestone.redis_demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class RedisDemoApplication

fun main(args: Array<String>) {
	runApplication<RedisDemoApplication>(*args)
}
