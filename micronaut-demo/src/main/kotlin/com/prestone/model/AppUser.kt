package com.prestone.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class AppUser(
    @field:Id
    @field:GeneratedValue
    val id: Long? = null,
    val username: String,
    val password: String,
    val address: Address
)
