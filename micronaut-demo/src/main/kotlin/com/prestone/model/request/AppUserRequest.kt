package com.prestone.model.request

data class AppUserRequest(
    val username: String,
    val password: String,
    val address: AddressRequest
)

data class AddressRequest(
    val street: String,
    val city: String,
    val state: String,
    val zip: String
)

data class SearchRequest(
    val username: String,
    val city: String
)