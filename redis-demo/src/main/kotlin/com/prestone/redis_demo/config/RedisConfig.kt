package com.prestone.redis_demo.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.connection.RedisClusterConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisNode
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

@Configuration
@EnableRedisRepositories
class RedisConfig(
    @Value("\${spring.data.redis.redis-mode-cluster}")
    private val redisIsModeCluster: Boolean,
    @Value("\${spring.data.cluster.cache-name.otp.ttl}")
    private val redisCacheNameOtpTtlMinutes: Long
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {

        val redisHost = System.getenv("REDIS-HOST") ?: "127.0.0.1"
        val redisPort = System.getenv("REDIS-PORT")?.toIntOrNull() ?: 6379

        if (redisIsModeCluster){
            val clusterConfig = RedisClusterConfiguration()
                .apply {
                    val redisNode = RedisNode(redisHost, redisPort)
                    addClusterNode(redisNode)
                }
            return LettuceConnectionFactory(clusterConfig)
        } else {
            val standaloneConfig = RedisStandaloneConfiguration(redisHost, redisPort.toInt())
            return LettuceConnectionFactory(standaloneConfig)
        }
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<Any, Any> {
        val template = RedisTemplate<Any, Any>()

        template.connectionFactory = redisConnectionFactory
        template.keySerializer = StringRedisSerializer()
        template.valueSerializer = StringRedisSerializer()

        return template
    }

    @Bean
    fun cacheManager(redisConnectionFactory: RedisConnectionFactory): RedisCacheManager {
        val config = RedisCacheConfiguration
            .defaultCacheConfig()
            .entryTtl(Duration.ofMinutes(redisCacheNameOtpTtlMinutes))
        return RedisCacheManager
            .builder(redisConnectionFactory)
            .cacheDefaults(config)
            .build()
    }
}