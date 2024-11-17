package com.prestone.model

import io.micronaut.serde.annotation.Serdeable.Deserializable
import io.micronaut.serde.annotation.Serdeable.Serializable

@Serializable
@Deserializable
data class Address(
    val street: String,
    val city: String,
    val state: String,
    val zip: String
)
